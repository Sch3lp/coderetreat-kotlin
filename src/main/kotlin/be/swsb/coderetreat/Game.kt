package be.swsb.coderetreat

import kotlin.reflect.KClass

data class Game private constructor(
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
            is Player1 -> copy(playerOneField = playerOneField.fire(point)).also { turnOrder.next(playerOneField.lastFireResult) }
            is Player2 -> copy(playerTwoField = playerTwoField.fire(point)).also { turnOrder.next(playerOneField.lastFireResult) }
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
            playerOneField.allShipsSunk() -> copy(winner = playerTwo)
            playerTwoField.allShipsSunk() -> copy(winner = playerOne)
            else -> this
        }

    fun place(player: Player, ship: Ship, startingPoint: Point, direction: Direction): Game =
        when (player) {
            is Player1 -> copy(playerOneField = playerOneField.place(ship, startingPoint, direction))
            is Player2 -> copy(playerTwoField = playerTwoField.place(ship, startingPoint, direction))
        }

    companion object {
        fun start(playerOne: String, playerTwo: String, turnOrder: KClass<out TurnOrder> = AlternatingTurnOrder::class): Game {
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

class ExtraFireOnHitTurnOrder(playerOne: Player, playerTwo: Player) : TurnOrder{
    private val hitsPerPlayer = mutableMapOf(playerOne to 0, playerTwo to 0)
    private val order = mutableListOf(playerOne, playerTwo)

    override fun requireTurn(player: Player, lazyMessage: () -> String) {
        require(order.first() == player, lazyMessage)
    }

    override fun next(lastFireResult: FireResult) {
        when(lastFireResult) {
            FireResult.NothingHappened -> order.reverse()
            FireResult.Miss -> order.reverse().also { hitsPerPlayer[order.last()] = 0 }
            FireResult.Hit -> if (hitsPerPlayer[order.first()] == 1) order.reverse().also { hitsPerPlayer[order.last()] = 0 }
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