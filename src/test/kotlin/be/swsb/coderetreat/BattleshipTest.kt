package be.swsb.coderetreat

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
    fun `shouldRenderShip - when renderPoint is at carrierPoint then return true`() {
        assertThat(shouldRenderShip(Point(1, 1), Point(1,1))).isTrue()
    }

    @Test
    fun `shouldRenderShip - when renderPoint is on a Point that represents a Carrier then also return true`() {
        val carrierPoint = Point(1, 1)
        val carrierLength = 5
        (1.. carrierLength).forEach { carriersX ->
            assertThat(shouldRenderShip(Point(carriersX, 1), carrierPoint)).isTrue()
        }
    }
}

fun renderField(carrierAt: Point? = null): String = (1..10).joinToString("\n") { y ->
    (1..10).joinToString("") { x ->
        if (shouldRenderShip(Point(x, y), carrierAt)) """⛴️"""
        else """🌊"""
    }
}

fun shouldRenderShip(renderPoint: Point, carrierAt: Point?): Boolean {
    return if (carrierAt == null) false
    else renderPoint in carrierAt + Point(5,0)
}

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point) : List<Point> =
        (this.x..this.x + other.x).map { this.copy(x = it) }
}