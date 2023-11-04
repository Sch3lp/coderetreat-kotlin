package be.swsb.coderetreat

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PointTest {
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