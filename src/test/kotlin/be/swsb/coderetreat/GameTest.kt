package be.swsb.coderetreat

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass

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
            .isThrownBy { game.fire(target = game.playerTwo, point = Point(1,1)) }

        val player1PlacedShips = game
            .place(game.playerOne, PatrolBoat, Point(1,1), Direction.Horizontally)
            .place(game.playerOne, Submarine, Point(1,2), Direction.Horizontally)
            .place(game.playerOne, Destroyer, Point(1,3), Direction.Horizontally)
            .place(game.playerOne, Battleship, Point(1,4), Direction.Horizontally)
            .place(game.playerOne, Carrier, Point(1,5), Direction.Horizontally)

        val bothPlayersPlacedShips = player1PlacedShips
            .place(game.playerTwo, PatrolBoat, Point(1,1), Direction.Vertically)
            .place(game.playerTwo, Submarine, Point(2,1), Direction.Vertically)
            .place(game.playerTwo, Destroyer, Point(3,1), Direction.Vertically)
            .place(game.playerTwo, Battleship, Point(4,1), Direction.Vertically)
            .place(game.playerTwo, Carrier, Point(5,1), Direction.Vertically)

        assertThatNoException()
            .isThrownBy {
                bothPlayersPlacedShips.fire(bothPlayersPlacedShips.playerTwo, Point(1,1))
            }
    }

}

data class Game private constructor(
    val playerOne: Player,
    val playerTwo: Player,
    private val playerOneField: PlayerField = PlayerField(),
    private val playerTwoField: PlayerField = PlayerField(),
) {

    fun fire(target: Player, point: Point): Game {
        check (playerOneField.isComplete() && playerTwoField.isComplete())
        return this
    }

    fun place(player: Player, ship: Ship, startingPoint: Point, direction: Direction) : Game {
        return when(player) {
            is Player1 -> copy(playerOneField = playerOneField.place(ship,startingPoint, direction))
            is Player2 -> copy(playerTwoField = playerTwoField.place(ship,startingPoint, direction))
        }
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
}

class Player1(name: String) : Player(name, "Red")
class Player2(name: String) : Player(name, "Blue")