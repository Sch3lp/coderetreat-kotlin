package be.swsb.coderetreat

import be.swsb.coderetreat.Direction.Horizontal
import be.swsb.coderetreat.Direction.Vertical
import be.swsb.coderetreat.Ship.Carrier
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test


class BattleshipTest {

    //start scherm
    //player name input
    //player1 setup battleships
    //pause scherm
    //player2 setup battleships
    //pause scherm
    //player1 hit coordinate to confirm
    //pause scherm
    //player2 hit to confirm

    @Test
    fun `An empty field is just ocean`() {
        val expected: String = """
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
        """.trimIndent()

        val actual: String = BattleshipField().render()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `A Carrier can be placed top left horizontally`() {
        val expected: String = """
            🛳️🛳️🛳️🛳️🛳️🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
        """.trimIndent()

        val actual: String = BattleshipField()
            .placeShip(
                ship = Carrier,
                x = 0,
                y = 0,
                direction = Horizontal
            )
            .render()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `a ship cannot be placed next to the board`() {
        assertThatExceptionOfType(IllegalArgumentException::class.java)
            .isThrownBy {
                BattleshipField()
                    .placeShip(Carrier, 8, 0, Horizontal)
            }
    }
}


class PlacedShipTest {

    @Test
    fun `a horizontally PlacedShip has positions that all have the same y axis`() {
        val positions: List<Pair<Int, Int>> = PlacedShip(
            0,
            0,
            Carrier,
            Horizontal
        ).positions

        assertThat(positions).containsExactly(
            Pair(0, 0),
            Pair(1, 0),
            Pair(2, 0),
            Pair(3, 0),
            Pair(4, 0),
        )
    }

    @Test
    fun `a vertically PlacedShip has positions that all have the same y axis`() {
        val positions: List<Pair<Int, Int>> = PlacedShip(
            0,
            0,
            Carrier,
            Vertical
        ).positions

        assertThat(positions).containsExactly(
            Pair(0, 0),
            Pair(0, 1),
            Pair(0, 2),
            Pair(0, 3),
            Pair(0, 4),
        )
    }
}