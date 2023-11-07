package be.swsb.coderetreat

import be.swsb.coderetreat.Direction.*
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class GameTest {

    @Test
    fun `A Game of Battleship cannot be started without player names`() {
        val game = Game.start(playerOne = "", playerTwo = "")
        assertThat(game).isEqualTo(Game.start("Red", "Blue"))
    }

    @Test
    fun `A Game of Battleship requires both players to have placed all their ships before they can start firing at each other`() {
        val game = Game.start("Bruce", "Selina")
        assertThatExceptionOfType(IllegalStateException::class.java)
            .isThrownBy { game.fire(target = game.playerTwo, point = Point(1, 1)) }

        val bothPlayersPlacedShips = game
            .withAllShipsPlacedInTopLeftCorner(game.playerOne, Horizontally)
            .withAllShipsPlacedInTopLeftCorner(game.playerTwo, Vertically)

        assertThatNoException()
            .isThrownBy {
                bothPlayersPlacedShips.fire(bothPlayersPlacedShips.playerTwo, Point(1, 1))
            }
    }

    @Test
    fun `A Game of Battleship is won when player one managed to sink all of player two's ships`() {
        val game = Game.start("Bruce", "Selina")
        val playerOne = game.playerOne
        val playerTwo = game.playerTwo
        val bothPlayersPlacedShips = game
            .withAllShipsPlacedInTopLeftCorner(playerOne, Horizontally)
            .withAllShipsPlacedInTopLeftCorner(playerTwo, Vertically)

        val player2ShipCoordinates = (Point(1, 1)..Point(1, 2)) +
                (Point(2, 1)..Point(2, 3)) +
                (Point(3, 1)..Point(3, 3)) +
                (Point(4, 1)..Point(4, 4)) +
                (Point(5, 1)..Point(5, 5))

        val player2ShipsSunk = player2ShipCoordinates.fold(bothPlayersPlacedShips) { acc, point ->
            acc.fire(playerTwo, point)
        }

        println(player2ShipsSunk.playerTwoField.render())

        assertThat(player2ShipsSunk.winner).isEqualTo(playerOne)
        assertThatExceptionOfType(IllegalStateException::class.java)
            .isThrownBy { player2ShipsSunk.fire(playerTwo, Point(9, 9)) }
            .withMessage("Game is over already!")
    }

    @Test
    fun `A Game of Battleship is won when player two managed to sink all of player one's ships`() {
        val game = Game.start("Bruce", "Selina")
        val playerOne = game.playerOne
        val playerTwo = game.playerTwo
        val bothPlayersPlacedShips = game
            .withAllShipsPlacedInTopLeftCorner(playerOne, Horizontally)
            .withAllShipsPlacedInTopLeftCorner(playerTwo, Vertically)

        val player1ShipCoordinates = (Point(1, 1)..Point(2, 1)) +
                (Point(1, 2)..Point(3, 2)) +
                (Point(1, 3)..Point(3, 3)) +
                (Point(1, 4)..Point(4, 4)) +
                (Point(1, 5)..Point(5, 5))

        val player1ShipsSunk = player1ShipCoordinates.fold(bothPlayersPlacedShips) { acc, point ->
            acc.fire(playerOne, point)
        }

        assertThat(player1ShipsSunk.winner).isEqualTo(playerTwo)
        assertThatExceptionOfType(IllegalStateException::class.java)
            .isThrownBy { player1ShipsSunk.fire(playerOne, Point(9, 9)) }
            .withMessage("Game is over already!")
    }

}

private fun Game.withAllShipsPlacedInTopLeftCorner(player: Player, direction: Direction): Game =
    Ship::class.sealedSubclasses
        .mapNotNull { it.objectInstance }
        .sortedBy { it.length }
        .foldIndexed(this) { index, game, ship ->
            game.place(
                player = player,
                ship = ship,
                startingPoint = when (direction) {
                    Horizontally -> Point(1, index + 1)
                    Vertically -> Point(index + 1, 1)
                },
                direction = direction
            )
        }

data class Game private constructor(
    val playerOne: Player,
    val playerTwo: Player,
    val playerOneField: PlayerField = PlayerField(),
    val playerTwoField: PlayerField = PlayerField(),
    val winner: Player? = null,
) {

    fun fire(target: Player, point: Point): Game {
        check(playerOneField.isComplete() && playerTwoField.isComplete())
        check(winner == null) { "Game is over already!" }
        return when (target) {
            is Player2 -> copy(playerTwoField = playerTwoField.fire(point))
            is Player1 -> copy(playerOneField = playerOneField.fire(point))
        }.orVictory()
    }

    private fun orVictory(): Game =
        when {
            playerTwoField.allShipsSunk() -> copy(winner = playerOne)
            playerOneField.allShipsSunk() -> copy(winner = playerTwo)
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