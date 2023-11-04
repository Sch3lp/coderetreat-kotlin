package be.swsb.coderetreat

import be.swsb.coderetreat.Direction.Horizontally
import be.swsb.coderetreat.Direction.Vertically
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class PlayerFieldTest {
    @Test
    fun `is initially empty`() {
        val actual = PlayerField()
        assertThat(actual).isEqualTo(PlayerField(emptyMap()))
    }

    @Test
    fun `contains a Carrier on 5 positions on the x-axis when it was placed horizontally`() {
        val actual: PlayerField = PlayerField().place(Carrier, Point(1, 1), Horizontally)
        val shipCoordinates = Point(1, 1)..Point(5, 1)
        val carrierPoints = shipCoordinates.map { it to """⛴️""" }

        assertThat(actual).isEqualTo(PlayerField(carrierPoints.toMap(), listOf(PlacedShip(Carrier.length, shipCoordinates))))
    }

    @Test
    fun `contains a Carrier on 5 positions on the x-axis when it was placed horizontally, on another y coordinate`() {
        val actual: PlayerField = PlayerField().place(Carrier, Point(1, 4), Horizontally)
        val shipCoordinates = Point(1, 4)..Point(5, 4)
        val carrierPoints = shipCoordinates.map { it to """⛴️""" }

        assertThat(actual).isEqualTo(PlayerField(carrierPoints.toMap(), listOf(PlacedShip(Carrier.length, shipCoordinates))))
    }

    @Test
    fun `contains a Carrier on 5 positions on the y-axis when it was placed vertically`() {
        val actual: PlayerField = PlayerField().place(Carrier, Point(1, 1), Vertically)
        val shipCoordinates = Point(1, 1)..Point(1, 5)
        val carrierPoints = shipCoordinates.map { it to """⛴️""" }

        assertThat(actual).isEqualTo(PlayerField(carrierPoints.toMap(), listOf(PlacedShip(Carrier.length, shipCoordinates))))
    }

    @Test
    fun `contains a Carrier on 5 positions on the y-axis when it was placed vertically, on another x coordinate`() {
        val actual: PlayerField = PlayerField().place(Carrier, Point(4, 1), Vertically)
        val shipCoordinates = Point(4, 1)..Point(4, 5)
        val carrierPoints = shipCoordinates.map { it to """⛴️""" }

        assertThat(actual).isEqualTo(PlayerField(carrierPoints.toMap(), listOf(PlacedShip(Carrier.length, shipCoordinates))))
    }

    @Test
    fun `should not accept a ship that would be placed out of bounds, vertically`() {
        assertThatExceptionOfType(PlacementOutOfBounds::class.java)
            .isThrownBy { PlayerField().place(Carrier, Point(1, -1), Vertically) }
            .withMessage("Placing a Carrier Vertically at Point(x=1, y=-1) is out of bounds")
        assertThatExceptionOfType(PlacementOutOfBounds::class.java)
            .isThrownBy { PlayerField().place(Carrier, Point(1, 9), Vertically) }
            .withMessage("Placing a Carrier Vertically at Point(x=1, y=9) is out of bounds")
    }

    @Test
    fun `should not accept a ship that would be placed out of bounds, horizontally`() {
        assertThatExceptionOfType(PlacementOutOfBounds::class.java)
            .isThrownBy { PlayerField().place(Carrier, Point(-1, 1), Horizontally) }
            .withMessage("Placing a Carrier Horizontally at Point(x=-1, y=1) is out of bounds")
        assertThatExceptionOfType(PlacementOutOfBounds::class.java)
            .isThrownBy { PlayerField().place(Carrier, Point(9, 1), Horizontally) }
            .withMessage("Placing a Carrier Horizontally at Point(x=9, y=1) is out of bounds")
    }

    @Test
    fun `firing on a placed Carrier, marks that specific carrier point as damaged`() {
        val actual = PlayerField()
            .place(Carrier, Point(5, 3), Vertically)
            .fire(Point(5, 3))

        val placedShips =
            listOf(PlacedShip(Carrier.length, listOf(Point(5, 3), Point(5, 4), Point(5, 5), Point(5, 6), Point(5, 7))))

        assertThat(actual).isEqualTo(
            PlayerField(
                grid = mapOf(
                    Point(5, 3) to """💥""",
                    Point(5, 4) to """⛴️""",
                    Point(5, 5) to """⛴️""",
                    Point(5, 6) to """⛴️""",
                    Point(5, 7) to """⛴️""",
                ),
                ships = placedShips

            )
        )
    }

    @Test
    fun `firing and missing a placed Carrier does not change the PlayerField`() {
        val actual = PlayerField()
            .place(Carrier, Point(5, 3), Vertically)
            .fire(Point(6, 3))

        val placedShips =
            listOf(PlacedShip(Carrier.length, listOf(Point(5, 3), Point(5, 4), Point(5, 5), Point(5, 6), Point(5, 7))))
        assertThat(actual).isEqualTo(
            PlayerField(
                grid = mapOf(
                    Point(5, 3) to """⛴️""",
                    Point(5, 4) to """⛴️""",
                    Point(5, 5) to """⛴️""",
                    Point(5, 6) to """⛴️""",
                    Point(5, 7) to """⛴️""",
                ),
                ships = placedShips
            )
        )
    }

    @Test
    @Disabled
    fun `firing and sinking a placed Carrier marks the Carrier as sunk`() {
        val actual = PlayerField()
            .place(Carrier, Point(5, 3), Vertically)
            .fire(Point(5, 3))
            .fire(Point(5, 4))
            .fire(Point(5, 5))
            .fire(Point(5, 6))
            .fire(Point(5, 7))

        assertThat(actual).isEqualTo(
            PlayerField(
                mapOf(
                    Point(5, 3) to """🏊""",
                    Point(5, 4) to """🏊""",
                    Point(5, 5) to """🏊""",
                    Point(5, 6) to """🏊""",
                    Point(5, 7) to """🏊""",
                )
            )
        )
    }
}