package be.swsb.coderetreat

import be.swsb.coderetreat.Direction.Horizontally
import be.swsb.coderetreat.Direction.Vertically
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
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

        val actual: String = PlayerField().renderField()

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
                .renderField()

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

        val actual: String =
            PlayerField().place(Carrier, Point(1, 1), Vertically)
                .renderField()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `renderPoint - when renderPoint is at carrierPoint then return ⛴️`() {
        assertThat(PlayerField().place(Carrier, Point(1, 1), Horizontally).renderPoint(Point(1, 1))).isEqualTo("""⛴️""")
    }

    @Test
    fun `renderPoint - when renderPoint is on a Point that represents a Carrier then also return ⛴️`() {
        val carrierLength = 5
        (1..carrierLength).forEach { carriersX ->
            assertThat(PlayerField().place(Carrier, Point(1, 1), Horizontally)
                .renderPoint(Point(carriersX, 1))).isEqualTo("""⛴️""")
        }
    }

    @Test
    fun `renderPoint - when renderPoint is outside of a Point that represents a Carrier then return 🌊`() {
        assertThat(PlayerField().place(Carrier, Point(1, 1), Horizontally).renderPoint(Point(6, 1))).isEqualTo("""🌊""")
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
            val actual: PlayerField = PlayerField().place(Carrier, Point(1, 1), Horizontally)
            val carrierPoints = (Point(1, 1)..Point(5, 1)).map { it to """⛴️""" }

            assertThat(actual).isEqualTo(PlayerField(carrierPoints.toMap()))
        }

        @Test
        fun `contains a Carrier on 5 positions on the x-axis when it was placed horizontally, on another y coordinate`() {
            val actual: PlayerField = PlayerField().place(Carrier, Point(1, 4), Horizontally)
            val carrierPoints = (Point(1, 4)..Point(5, 4)).map { it to """⛴️""" }

            assertThat(actual).isEqualTo(PlayerField(carrierPoints.toMap()))
        }

        @Test
        fun `contains a Carrier on 5 positions on the y-axis when it was placed vertically`() {
            val actual: PlayerField = PlayerField().place(Carrier, Point(1, 1), Vertically)
            val carrierPoints = (Point(1, 1)..Point(1, 5)).map { it to """⛴️""" }

            assertThat(actual).isEqualTo(PlayerField(carrierPoints.toMap()))
        }

        @Test
        fun `contains a Carrier on 5 positions on the y-axis when it was placed vertically, on another x coordinate`() {
            val actual: PlayerField = PlayerField().place(Carrier, Point(4, 1), Vertically)
            val carrierPoints = (Point(4, 1)..Point(4, 5)).map { it to """⛴️""" }

            assertThat(actual).isEqualTo(PlayerField(carrierPoints.toMap()))
        }
    }

    @Nested
    inner class `A Point` {
        @Test
        fun `can be added to another Point`() {
            val actual = Point(1, 2) + Point(2, -4)
            assertThat(actual).isEqualTo(Point(3, -2))
        }

        @Test
        fun `can be ranged to another Point to produce a list of Points on the x axis when there's a matching y coordinate`() {
            val actual = Point(-3, 4)..Point(1, 4)
            assertThat(actual).isEqualTo(
                listOf(
                    Point(-3, 4),
                    Point(-2, 4),
                    Point(-1, 4),
                    Point(0, 4),
                    Point(1, 4),
                )
            )
        }

        @Test
        fun `can be ranged to another Point to produce a list of Points on the y axis when there's a matching x coordinate`() {
            val actual = Point(4, -3)..Point(4, 1)
            assertThat(actual).isEqualTo(
                listOf(
                    Point(4, -3),
                    Point(4, -2),
                    Point(4, -1),
                    Point(4, 0),
                    Point(4, 1),
                )
            )
        }

        @Test
        fun `cannot be ranged to another Point when no match on either x or y coordinate`() {
            val actual = Point(5, -3)..Point(4, 1)
            assertThat(actual).isEqualTo(emptyList<Point>())
        }

    }
}

data class PlayerField(private val grid: Map<Point, String> = emptyMap()) {
    fun place(ship: Ship, startingPoint: Point, direction: Direction): PlayerField {
        val shipCoordinates = when (direction) {
            Horizontally -> startingPoint..<(startingPoint + Point(ship.length, 0))
            Vertically -> startingPoint..<(startingPoint + Point(0, ship.length))
        }
        val newGrid = grid + shipCoordinates.map { it to ship.representation }.toMap()
        return copy(grid = newGrid)
    }

    private fun get(point: Point): String? {
        return grid[point]
    }

    fun renderField(): String = (1..10).joinToString("\n") { y ->
        (1..10).joinToString("") { x ->
            this.renderPoint(Point(x, y))
        }
    }

    fun renderPoint(renderPoint: Point): String =
        if (get(renderPoint) != null) get(renderPoint)!!
        else """🌊"""
}

sealed class Ship(val representation: String, val length: Int)
data object Carrier : Ship("""⛴️""", 5)

data class Point(val x: Int, val y: Int) {
    operator fun rangeTo(other: Point): List<Point> {
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

    operator fun rangeUntil(other: Point) = (this..other).dropLast(1)

    operator fun plus(other: Point) =
        Point(this.x + other.x, this.y + other.y)
}

enum class Direction {
    Horizontally,
    Vertically,
}