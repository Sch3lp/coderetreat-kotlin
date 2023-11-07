package be.swsb.coderetreat

data class Game private constructor(
    val playerOne: Player,
    val playerTwo: Player,
    val playerOneField: PlayerField = PlayerField(),
    val playerTwoField: PlayerField = PlayerField(),
    val winner: Player? = null,
) {

    fun fire(target: Player, point: Point): Game {
        check(playerOneField.isComplete() && playerTwoField.isComplete()) { "Both players are required to place all of their ships first" }
        check(winner == null) { "Game is over already!" }
        return when (target) {
            is Player1 -> copy(playerOneField = playerOneField.fire(point))
            is Player2 -> copy(playerTwoField = playerTwoField.fire(point))
        }.orVictory()
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
        fun start(playerOne: String, playerTwo: String) = Game(Player1(playerOne), Player2(playerTwo))
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