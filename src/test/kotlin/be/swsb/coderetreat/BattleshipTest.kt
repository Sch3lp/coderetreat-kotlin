package be.swsb.coderetreat

import be.swsb.coderetreat.Direction.Horizontally
import be.swsb.coderetreat.Direction.Vertically
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.math.absoluteValue

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

        val actual: String = renderField(direction = Horizontally)

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

        val actual: String = renderField(carrierAt = Point(1,1), direction = Horizontally)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    @Disabled
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

        val actual: String = renderField(carrierAt = Point(1,1), direction = Vertically)

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

    @Nested
    inner class `A PlayerField` {
        @Test
        fun `is initially empty`() {
            val actual = PlayerField()
            assertThat(actual).isEqualTo(PlayerField(emptyMap()))
        }

        @Test
        fun `contains a Carrier on 5 positions on the x-axis when it was placed horizontally`() {
            val actual : PlayerField = PlayerField().place(Carrier, Point(1,1), Horizontally)
            val carrierPoints = (Point(1, 1) .. Point(5,1)).map { it to """⛴️""" }

            assertThat(actual).isEqualTo(PlayerField(carrierPoints.toMap()))
        }

        @Test
        @Disabled
        fun `contains a Carrier on 5 positions on the y-axis when it was placed vertically`() {
            val actual : PlayerField = PlayerField().place(Carrier, Point(1,1), Vertically)
            val carrierPoints = (Point(1, 1) .. Point(1,5)).map { it to """⛴️""" }

            assertThat(actual).isEqualTo(PlayerField(carrierPoints.toMap()))
        }
    }

    @Nested
    inner class `A Point` {
        @Test
        fun `can be added to another Point`() {
            val actual = Point(1,2) + Point(2,-4)
            assertThat(actual).isEqualTo(Point(3,-2))
        }

        @Test
        fun `can be ranged to another Point to produce a list of Points on the x axis when there's a matching y coordinate`() {
            val actual = Point(-3,4) .. Point(1,4)
            assertThat(actual).isEqualTo(listOf(
                Point(-3,4),
                Point(-2,4),
                Point(-1,4),
                Point(0,4),
                Point(1,4),
            ))
        }

        @Test
        fun `can be ranged to another Point to produce a list of Points on the y axis when there's a matching x coordinate`() {
            val actual = Point(4,-3) .. Point(4,1)
            assertThat(actual).isEqualTo(listOf(
                Point(4,-3),
                Point(4,-2),
                Point(4,-1),
                Point(4,0),
                Point(4,1),
            ))
        }

        //rangeTo is empty when no matching x or y
    }
}

data class PlayerField(private val grid: Map<Point,String> = emptyMap()) {
    fun place(ship: Ship, startingPoint: Point, horizontally: Direction): PlayerField {
        val newGrid = grid + (startingPoint .. Point(ship.length, 1)).map { it to ship.representation }.toMap()
        return copy(grid = newGrid)
    }
}

sealed class Ship(val representation: String, val length: Int)
data object Carrier : Ship("""⛴️""", 5)

fun renderField(carrierAt: Point? = null, direction: Direction): String = (1..10).joinToString("\n") { y ->
    (1..10).joinToString("") { x ->
        renderPoint(Point(x, y), carrierAt)
    }
}

fun renderPoint(renderPoint: Point, carrierAt: Point?): String =
    if ((carrierAt != null) && (renderPoint in carrierAt .. Point(5,1))) """⛴️"""
    else """🌊"""

data class Point(val x: Int, val y: Int) {
    operator fun rangeTo(other: Point) : List<Point> {
        return when {
            this.y == other.y -> {
                val pointsBetween = if (this.x > other.x) this.x - other.x else other.x - this.x
                (this.x..this.x + pointsBetween).map { this.copy(x = it) }
            }
            this.x == other.x -> {
                val pointsBetween = if (this.y > other.y) this.y - other.y else other.y - this.y
                (this.y..this.y + pointsBetween).map { this.copy(y = it) }
            }
            else -> emptyList()
        }
    }

    operator fun plus(other: Point) =
        Point(this.x + other.x, this.y+other.y)
}

enum class Direction {
    Horizontally,
    Vertically,
}