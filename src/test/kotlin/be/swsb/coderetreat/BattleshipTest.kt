package be.swsb.coderetreat

import org.assertj.core.api.Assertions.assertThat
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
            .placeShip(ship = Ship.Carrier, x = 0, y = 0, direction = Direction.Horizontal)
            .render()

        assertThat(actual).isEqualTo(expected)
    }
}



