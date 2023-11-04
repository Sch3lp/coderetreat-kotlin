package be.swsb.coderetreat

import be.swsb.coderetreat.Direction.Horizontally
import be.swsb.coderetreat.Direction.Vertically
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test

class PlayerFieldTest {
    @Test
    fun `is initially empty`() {
        val actual = PlayerField()
        assertThat(actual).isEqualTo(PlayerField(emptyList()))
    }

    @Test
    fun `contains a Carrier on 5 positions on the x-axis when it was placed horizontally`() {
        val actual: PlayerField = PlayerField().place(Carrier, Point(1, 1), Horizontally)
        val carrierPoints = (Point(1, 1)..Point(5, 1)).toSet()

        assertThat(actual).isEqualTo(PlayerField(listOf(PlacedShip(Carrier, carrierPoints))))
    }

    @Test
    fun `contains a Carrier on 5 positions on the x-axis when it was placed horizontally, on another y coordinate`() {
        val actual: PlayerField = PlayerField().place(Carrier, Point(1, 4), Horizontally)
        val carrierPoints = (Point(1, 4)..Point(5, 4)).toSet()

        assertThat(actual).isEqualTo(PlayerField(listOf(PlacedShip(Carrier, carrierPoints))))
    }

    @Test
    fun `contains a Carrier on 5 positions on the y-axis when it was placed vertically`() {
        val actual: PlayerField = PlayerField().place(Carrier, Point(1, 1), Vertically)
        val carrierPoints = (Point(1, 1)..Point(1, 5)).toSet()

        assertThat(actual).isEqualTo(PlayerField(listOf(PlacedShip(Carrier, carrierPoints))))
    }

    @Test
    fun `contains a Carrier on 5 positions on the y-axis when it was placed vertically, on another x coordinate`() {
        val actual: PlayerField = PlayerField().place(Carrier, Point(4, 1), Vertically)
        val carrierPoints = (Point(4, 1)..Point(4, 5)).toSet()

        assertThat(actual).isEqualTo(PlayerField(listOf(PlacedShip(Carrier, carrierPoints))))
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
    fun `should not accept a ship that was already placed`() {
        val playerField = PlayerField().place(Carrier, Point(1, 1), Horizontally)
        assertThatExceptionOfType(Cheater::class.java)
            .isThrownBy { playerField.place(Carrier, Point(1,3), Horizontally) }
            .withMessage("Where did you get that extra Carrier from? Cheater!")
    }

    @Test
    fun `firing on a placed Carrier, marks that specific carrier point as damaged`() {
        val actual = PlayerField()
                .place(Carrier, Point(5, 3), Vertically)
                .fire(Point(5,3))

        val damagedCarrier = PlacedShip(Carrier, (Point(5, 3)..Point(5, 7)).toSet())
            .damage(Point(5,3))
        assertThat(actual).isEqualTo(PlayerField(listOf(damagedCarrier)))
    }

    @Test
    fun `firing and missing a placed Carrier does not change the PlayerField`() {
        val actual = PlayerField()
                .place(Carrier, Point(5, 3), Vertically)
                .fire(Point(6,3))

        assertThat(actual).isEqualTo(
            PlayerField(listOf(PlacedShip(Carrier, (Point(5, 3)..Point(5, 7)).toSet())))
        )
    }

    @Test
    fun `firing and sinking a placed Carrier marks the Carrier as sunk`() {
        val actual = PlayerField()
                .place(Carrier, Point(5, 3), Vertically)
                .fire(Point(5,3))
                .fire(Point(5,4))
                .fire(Point(5,5))
                .fire(Point(5,6))
                .fire(Point(5,7))

        assertThat(actual).isEqualTo(
            PlayerField(listOf(PlacedShip(Carrier, (Point(5, 3)..Point(5, 7)).toSet(), (Point(5, 3)..Point(5, 7)).toSet())))
        )
    }
}