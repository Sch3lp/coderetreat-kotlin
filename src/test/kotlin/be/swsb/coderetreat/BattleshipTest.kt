package be.swsb.coderetreat

import be.swsb.coderetreat.Direction.Horizontally
import be.swsb.coderetreat.Direction.Vertically
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class BattleshipTest {

    @Test
    fun `A player's field is renderable`() {
        val expected = """
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

        val actual: String = PlayerField().render()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `A carrier can be placed horizontally on this field`() {
        val expected = """
            ⛴️⛴️⛴️⛴️⛴️🌊🌊🌊🌊🌊
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

        val actual: String =
            PlayerField().place(Carrier, Point(1, 1), Horizontally)
                .render()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `A carrier can be placed vertically on this field`() {
        val expected = """
            ⛴️🌊🌊🌊🌊🌊🌊🌊🌊🌊
            ⛴️🌊🌊🌊🌊🌊🌊🌊🌊🌊
            ⛴️🌊🌊🌊🌊🌊🌊🌊🌊🌊
            ⛴️🌊🌊🌊🌊🌊🌊🌊🌊🌊
            ⛴️🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
        """.trimIndent()

        val actual: String =
            PlayerField().place(Carrier, Point(1, 1), Vertically)
                .render()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `A carrier can be placed vertically on the middle of a player field`() {
        val expected = """
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊⛴️🌊🌊🌊🌊🌊
            🌊🌊🌊🌊⛴️🌊🌊🌊🌊🌊
            🌊🌊🌊🌊⛴️🌊🌊🌊🌊🌊
            🌊🌊🌊🌊⛴️🌊🌊🌊🌊🌊
            🌊🌊🌊🌊⛴️🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
        """.trimIndent()

        val actual: String =
            PlayerField().place(Carrier, Point(5, 3), Vertically)
                .render()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Firing can hit a placed carrier`() {
        val expected = """
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊💥🌊🌊🌊🌊🌊
            🌊🌊🌊🌊⛴️🌊🌊🌊🌊🌊
            🌊🌊🌊🌊⛴️🌊🌊🌊🌊🌊
            🌊🌊🌊🌊⛴️🌊🌊🌊🌊🌊
            🌊🌊🌊🌊⛴️🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
        """.trimIndent()

        val actual: String =
            PlayerField()
                .place(Carrier, Point(5, 3), Vertically)
                .fire(Point(5,3))
                .render()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Firing can sink a placed carrier`() {
        val expected = """
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🏊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🏊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🏊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🏊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🏊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
        """.trimIndent()

        val actual: String =
            PlayerField()
                .place(Carrier, Point(5, 3), Vertically)
                .fire(Point(5,3))
                .fire(Point(5,4))
                .fire(Point(5,5))
                .fire(Point(5,6))
                .fire(Point(5,7))
                .render()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `A fully placed PlayerField`() {
        val expected = """
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊⛴️⛴️⛴️⛴️⛴️🌊🌊🌊
            🌊🌊🛳️🛳️🛳️🛳️🌊🌊🌊🌊
            🌊🌊🛥️🛥️🛥️🌊🌊🌊🌊🌊
            🌊🌊🚤🚤🚤🌊🌊🌊🌊🌊
            🌊🌊🛶🛶🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
            🌊🌊🌊🌊🌊🌊🌊🌊🌊🌊
        """.trimIndent()

        val actual: String =
            PlayerField()
                .place(Carrier, Point(3, 3), Horizontally)
                .place(Battleship, Point(3, 4), Horizontally)
                .place(Destroyer, Point(3, 5), Horizontally)
                .place(Submarine, Point(3, 6), Horizontally)
                .place(PatrolBoat, Point(3, 7), Horizontally)
                .render()

        assertThat(actual).isEqualTo(expected)
    }
}