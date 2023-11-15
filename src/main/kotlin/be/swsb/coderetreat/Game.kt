package be.swsb.coderetreat

import be.swsb.coderetreat.FireResult.*
import kotlin.reflect.KClass

class Game private constructor(
    val playerOne: Player1,
    val playerTwo: Player2,
    val playerOneField: PlayerField = PlayerField(),
    val playerTwoField: PlayerField = PlayerField(),
    val winner: Player? = null,
    private val turnOrder: TurnOrder,
) {

    fun fire(target: Player, point: Point): Game {
        check(playerOneField.isComplete() && playerTwoField.isComplete()) { "Both players are required to place all of their ships first" }
        check(winner == null) { "Game is over already!" }
        val player = target.opponent
        turnOrder.requireTurn(player) { "Played out of turn! Right now it's ${player.opponent.javaClass.simpleName}'s turn." }
        return when (target) {
            is Player1 -> updateFields(playerOneField = playerOneField.fire(point).also { turnOrder.next(it.lastFireResult) })
            is Player2 -> updateFields(playerTwoField = playerTwoField.fire(point).also { turnOrder.next(it.lastFireResult) })
        }.orVictory()
    }

    private val Player.opponent: Player
        get() =
            when (this) {
                is Player1 -> playerTwo
                is Player2 -> playerOne
            }

    private fun orVictory(): Game =
        when {
            playerOneField.allShipsSunk() -> declareWinner(playerTwo)
            playerTwoField.allShipsSunk() -> declareWinner(playerOne)
            else -> this
        }

    private fun declareWinner(winner: Player) = copy(winner = winner)

    fun place(player: Player, ship: Ship, startingPoint: Point, direction: Direction): Game =
        when (player) {
            is Player1 -> updateFields(playerOneField = playerOneField.place(ship, startingPoint, direction))
            is Player2 -> updateFields(playerTwoField = playerTwoField.place(ship, startingPoint, direction))
        }

    private fun updateFields(
        playerOneField: PlayerField = this.playerOneField,
        playerTwoField: PlayerField = this.playerTwoField,
    ) = copy(playerOneField = playerOneField, playerTwoField = playerTwoField)


    private fun copy(
        playerOne: Player1 = this.playerOne,
        playerTwo: Player2 = this.playerTwo,
        playerOneField: PlayerField = this.playerOneField,
        playerTwoField: PlayerField = this.playerTwoField,
        winner: Player? = this.winner,
        turnOrder: TurnOrder = this.turnOrder,
    ) = Game(playerOne, playerTwo, playerOneField, playerTwoField, winner, turnOrder)

    //Because data class private constructor still has a public "constructor" via .copy() function
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Game

        if (playerOne != other.playerOne) return false
        if (playerTwo != other.playerTwo) return false
        if (playerOneField != other.playerOneField) return false
        if (playerTwoField != other.playerTwoField) return false
        if (winner != other.winner) return false
        if (turnOrder != other.turnOrder) return false

        return true
    }
    override fun hashCode(): Int {
        var result = playerOne.hashCode()
        result = 31 * result + playerTwo.hashCode()
        result = 31 * result + playerOneField.hashCode()
        result = 31 * result + playerTwoField.hashCode()
        result = 31 * result + (winner?.hashCode() ?: 0)
        result = 31 * result + turnOrder.hashCode()
        return result
    }

    companion object {
        fun start(
            playerOne: String,
            playerTwo: String,
            turnOrder: KClass<out TurnOrder> = AlternatingTurnOrder::class
        ): Game {
            val player1 = Player1(playerOne)
            val player2 = Player2(playerTwo)
            val turnOrderStrategy = when (turnOrder) {
                AlternatingTurnOrder::class -> AlternatingTurnOrder(player1, player2)
                ExtraFireOnHitTurnOrder::class -> ExtraFireOnHitTurnOrder(player1, player2)
                else -> error("Unknown TurnOrder strategy: $turnOrder")
            }
            return Game(player1, player2, turnOrder = turnOrderStrategy)
        }
    }
}

enum class FireResult {
    NothingHappened,
    Hit,
    Miss,
}

sealed interface TurnOrder {
    fun requireTurn(player: Player, lazyMessage: () -> String)
    fun next(lastFireResult: FireResult)
}

class AlternatingTurnOrder(playerOne: Player, playerTwo: Player) : TurnOrder {
    private val order = mutableListOf(playerOne, playerTwo)

    override fun requireTurn(player: Player, lazyMessage: () -> String) =
        require(order.first() == player, lazyMessage)

    override fun next(lastFireResult: FireResult) =
        order.reverse()
}

class ExtraFireOnHitTurnOrder(playerOne: Player, playerTwo: Player) : TurnOrder {
    private val hitsPerPlayer = mutableMapOf(playerOne to 0, playerTwo to 0)
    private val order = mutableListOf(playerOne, playerTwo)

    override fun requireTurn(player: Player, lazyMessage: () -> String) {
        require(order.first() == player, lazyMessage)
    }

    override fun next(lastFireResult: FireResult) {
        when (lastFireResult) {
            NothingHappened -> order.reverse()
            Miss -> order.reverse().also { hitsPerPlayer[order.last()] = 0 }
            Hit -> if (hitsPerPlayer[order.first()] == 1) order.reverse().also { hitsPerPlayer[order.last()] = 0 }
            else hitsPerPlayer[order.first()] = 1
        }
    }
}

sealed class Player(name: String, default: String) {
    val name: String = name.ifBlank { default }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Player

        return name == other.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun toString(): String = "${this.javaClass.simpleName}: $name"
}

class Player1(name: String) : Player(name, "Red")
class Player2(name: String) : Player(name, "Blue")