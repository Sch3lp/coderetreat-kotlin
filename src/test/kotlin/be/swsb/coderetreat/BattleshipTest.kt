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
    @Disabled
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
        assertThat(shouldRenderShip(1,1, Point(1,1))).isTrue()
    }

    @Test
    fun `shouldRenderShip - when renderPoint is at carrierPoint's x + 4 then also return true`() {
        val carrierPoint = Point(1, 1)
        val actual = shouldRenderShip(carrierPoint.x + 4, 1, carrierPoint)
        assertThat(actual).isTrue()
    }
}

fun renderField(carrierAt: Point? = null): String = (1..10).joinToString("\n") { y ->
    (1..10).joinToString("") { x ->
        if (shouldRenderShip(x, y, carrierAt)) """⛴️"""
        else """🌊"""
    }
}

fun shouldRenderShip(x: Int, y: Int, carrierAt: Point?) = Point(x, y) == carrierAt

data class Point(val x: Int, val y: Int)