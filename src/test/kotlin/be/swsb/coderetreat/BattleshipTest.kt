package be.swsb.coderetreat

import org.assertj.core.api.Assertions.assertThat
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

        val actual: String = renderField()

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

        val actual: String = renderField(carrierAt = Point(1,1))

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `renderPoint - when renderPoint is at carrierPoint then return ⛴️`() {
        assertThat(renderPoint(Point(1, 1), Point(1,1))).isEqualTo("""⛴️""")
    }

    @Test
    fun `renderPoint - when renderPoint is on a Point that represents a Carrier then also return ⛴️`() {
        val carrierPoint = Point(1, 1)
        val carrierLength = 5
        (1.. carrierLength).forEach { carriersX ->
            assertThat(renderPoint(Point(carriersX, 1), carrierPoint)).isEqualTo("""⛴️""")
        }
    }

    @Test
    fun `renderPoint - when renderPoint is outside of a Point that represents a Carrier then return 🌊`() {
        val carrierPoint = Point(1, 1)
        assertThat(renderPoint(Point(6, 1), carrierPoint)).isEqualTo("""🌊""")
    }
}

fun renderField(carrierAt: Point? = null): String = (1..10).joinToString("\n") { y ->
    (1..10).joinToString("") { x ->
        renderPoint(Point(x, y), carrierAt)
    }
}

fun renderPoint(renderPoint: Point, carrierAt: Point?): String =
    if ((carrierAt != null) && (renderPoint in carrierAt + Point(5,0))) """⛴️"""
    else """🌊"""

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point) : List<Point> =
        (this.x..< this.x + other.x).map { this.copy(x = it) }
}