package be.swsb.coderetreat

import be.swsb.coderetreat.TurnOrderType.Alternating
import kotlin.properties.Delegates

sealed interface TurnOrder : GameEventListener {
    fun requireTurn(player: Player, lazyMessage: () -> String)
    fun next()
    val type: TurnOrderType
}

enum class TurnOrderType {
    Alternating,
    ExtraFireOnHit,
}

class AlternatingTurnOrder(playerOne: Player, playerTwo: Player) : TurnOrder {
    private val order = mutableListOf(playerOne, playerTwo)

    override val type: TurnOrderType = Alternating

    override fun requireTurn(player: Player, lazyMessage: () -> String) =
        require(order.first() == player, lazyMessage)

    override fun next() =
        order.reverse()

    override fun receive(event: String) {
        //noop?
    }

}

interface GameEventListener {
    fun receive(event: String)
}

class ExtraFireOnHitTurnOrder : GameEventListener{
    override fun receive(event: String) {
    }
}

class GameEvents(events: List<String> = emptyList()) {
    private lateinit var listener: GameEventListener

    fun hook(turnOrder: TurnOrder) {
        this.listener = turnOrder
    }

    val gameEvents by Delegates.observable(events) { _, old, new ->
        if (old != new) {
            listener.receive((new - old).first())
        }
    }
}

data class Game private constructor(
    val playerOne: Player1,
    val playerTwo: Player2,
    val playerOneField: PlayerField = PlayerField(),
    val playerTwoField: PlayerField = PlayerField(),
    val winner: Player? = null,
    private val events: GameEvents = GameEvents(),
    private val turnOrder: TurnOrder,
) {

    fun fire(target: Player, point: Point): Game {
        check(playerOneField.isComplete() && playerTwoField.isComplete()) { "Both players are required to place all of their ships first" }
        check(winner == null) { "Game is over already!" }
        val player = target.opponent
        turnOrder.requireTurn(player) { "Played out of turn! Right now it's ${player.opponent.javaClass.simpleName}'s turn." }
        return when (target) {
            is Player1 -> copy(playerOneField = playerOneField.fire(point))
            is Player2 -> copy(playerTwoField = playerTwoField.fire(point))
        }.passTurn().orVictory()
    }

    private val Player.opponent: Player
        get() =
            when (this) {
                is Player1 -> playerTwo
                is Player2 -> playerOne
            }


    private fun passTurn(): Game =
        this.also { turnOrder.next() }

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
        fun start(playerOne: String, playerTwo: String, turnOrder: TurnOrderType = Alternating): Game {
            val player1 = Player1(playerOne)
            val player2 = Player2(playerTwo)
            val turnOrderStrategy = when (turnOrder) {
                Alternating -> AlternatingTurnOrder(player1, player2)
                else -> AlternatingTurnOrder(player1, player2)
            }
            return Game(player1, player2, turnOrder = turnOrderStrategy).init()
        }
    }

    private fun init(): Game = this.also { events.hook(turnOrder) }
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