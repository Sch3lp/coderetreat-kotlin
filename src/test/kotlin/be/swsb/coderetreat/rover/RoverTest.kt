package be.swsb.coderetreat.rover

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import be.swsb.coderetreat.planet.Mars
import be.swsb.coderetreat.planet.Moon
import org.junit.Test

class RoverTest {

    @Test
    fun `a default Rover should be facing North`() {
        val defaultRover = Rover()

        assertThat(defaultRover.facingDirection).isEqualTo(Direction.NORTH)
    }

    @Test
    fun `a default Rover should be positioned at (0,0)`() {
        val defaultRover = Rover()

        assertThat(defaultRover.position).isEqualTo(Position(0, 0))
    }

    @Test
    fun `a Rover on the moon, when rotating, should stay on the moon`() {
        val aRover = Rover(planet = Moon)

        val actual = aRover.receiveCommand(Left)

        assertThat(actual.planet).isEqualTo(Moon)
    }

    @Test
    fun `a Rover on the moon, when moving, should stay on the moon`() {
        val aRover = Rover(planet = Moon)

        val actual = aRover.receiveCommand(Forwards)

        assertThat(actual.planet).isEqualTo(Moon)
    }

    @Test
    fun `a default Rover should be on the planet Mars without any obstacles`() {
        val defaultRover = Rover()

        assertThat(defaultRover.planet).isEqualTo(Mars())
    }

    @Test
    fun `a Rover facing North, upon receiving forwards should move 1 position North`() {
        val aRover = Rover(facingDirection = Direction.NORTH)

        val movedRover = aRover.receiveCommand(Forwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(0, 1), facingDirection = Direction.NORTH))
    }

    @Test
    fun `a Rover facing East, upon receiving forwards should move 1 position East`() {
        val aRover = Rover(facingDirection = Direction.EAST)

        val movedRover = aRover.receiveCommand(Forwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(1, 0), facingDirection = Direction.EAST))
    }

    @Test
    fun `a Rover facing South, upon receiving forwards should move 1 position South`() {
        val aRover = Rover(facingDirection = Direction.SOUTH)

        val movedRover = aRover.receiveCommand(Forwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(0, -1), facingDirection = Direction.SOUTH))
    }

    @Test
    fun `a Rover facing West, upon receiving forwards should move 1 position West`() {
        val aRover = Rover(facingDirection = Direction.WEST)

        val movedRover = aRover.receiveCommand(Forwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(-1, 0), facingDirection = Direction.WEST))
    }

    @Test
    fun `a Rover facing North, upon receiving forwards twice, should move 2 positions North`() {
        val aRover = Rover(facingDirection = Direction.NORTH)

        val movedRover = aRover.receiveCommand(Forwards).receiveCommand(Forwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(0, 2), facingDirection = Direction.NORTH))
    }

    @Test
    fun `a Rover facing East, upon receiving forwards twice, should move 2 positions East`() {
        val aRover = Rover(facingDirection = Direction.EAST)

        val movedRover = aRover.receiveCommand(Forwards).receiveCommand(Forwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(2, 0), facingDirection = Direction.EAST))
    }

    @Test
    fun `a Rover facing South, upon receiving forwards twice, should move 2 positions South`() {
        val aRover = Rover(facingDirection = Direction.SOUTH)

        val movedRover = aRover.receiveCommand(Forwards).receiveCommand(Forwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(0, -2), facingDirection = Direction.SOUTH))
    }

    @Test
    fun `a Rover facing West, upon receiving forwards twice, should move 2 positions West`() {
        val aRover = Rover(facingDirection = Direction.WEST)

        val movedRover = aRover.receiveCommand(Forwards).receiveCommand(Forwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(-2, 0), facingDirection = Direction.WEST))
    }

    @Test
    fun `a Rover facing North, upon receiving backwards should move 1 position South`() {
        val aRover = Rover(facingDirection = Direction.NORTH)

        val movedRover = aRover.receiveCommand(Backwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(0, -1), facingDirection = Direction.NORTH))
    }

    @Test
    fun `a Rover facing East, upon receiving backwards should move 1 position West`() {
        val aRover = Rover(facingDirection = Direction.EAST)

        val movedRover = aRover.receiveCommand(Backwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(-1, 0), facingDirection = Direction.EAST))
    }

    @Test
    fun `a Rover facing South, upon receiving backwards should move 1 position North`() {
        val aRover = Rover(facingDirection = Direction.SOUTH)

        val movedRover = aRover.receiveCommand(Backwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(0, 1), facingDirection = Direction.SOUTH))
    }

    @Test
    fun `a Rover facing West, upon receiving backwards should move 1 position East`() {
        val aRover = Rover(facingDirection = Direction.WEST)

        val movedRover = aRover.receiveCommand(Backwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(1, 0), facingDirection = Direction.WEST))
    }

    @Test
    fun `a Rover facing North, upon receiving backwards twice, should move 2 positions South`() {
        val aRover = Rover(facingDirection = Direction.NORTH)

        val movedRover = aRover.receiveCommand(Backwards).receiveCommand(Backwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(0, -2), facingDirection = Direction.NORTH))
    }

    @Test
    fun `a Rover facing East, upon receiving backwards twice, should move 2 positions West`() {
        val aRover = Rover(facingDirection = Direction.EAST)

        val movedRover = aRover.receiveCommand(Backwards).receiveCommand(Backwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(-2, 0), facingDirection = Direction.EAST))
    }

    @Test
    fun `a Rover facing South, upon receiving backwards twice, should move 2 positions North`() {
        val aRover = Rover(facingDirection = Direction.SOUTH)

        val movedRover = aRover.receiveCommand(Backwards).receiveCommand(Backwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(0, 2), facingDirection = Direction.SOUTH))
    }

    @Test
    fun `a Rover facing West, upon receiving backwards twice, should move 2 positions East`() {
        val aRover = Rover(facingDirection = Direction.WEST)

        val movedRover = aRover.receiveCommand(Backwards).receiveCommand(Backwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(2, 0), facingDirection = Direction.WEST))
    }

    @Test
    fun `a Rover facing North, upon receiving right, rotates to East`() {
        val aRover = Rover(facingDirection = Direction.NORTH)

        val rotatedRover = aRover.receiveCommand(Right)

        assertThat(rotatedRover).isEqualTo(Rover(facingDirection = Direction.EAST, position = aRover.position))
    }

    @Test
    fun `a Rover facing North, upon receiving right 4 times, rotates back to North`() {
        val aRover = Rover(facingDirection = Direction.NORTH)

        val rotatedRover = aRover.receiveCommand(Right).receiveCommand(Right).receiveCommand(Right).receiveCommand(Right)

        assertThat(rotatedRover).isEqualTo(Rover(facingDirection = Direction.NORTH, position = aRover.position))
    }

    @Test
    fun `a Rover facing North, upon receiving left 4 times, rotates back to North`() {
        val aRover = Rover(facingDirection = Direction.NORTH)

        val rotatedRover = aRover.receiveCommand(Left).receiveCommand(Left).receiveCommand(Left).receiveCommand(Left)

        assertThat(rotatedRover).isEqualTo(Rover(facingDirection = Direction.NORTH, position = aRover.position))
    }

    @Test
    fun `a Rover on the Planets' top edge, upon moving forwards North, should be positioned at the Planets' bottom edge`() {
        val aRover = Rover(facingDirection = Direction.NORTH, position = Position(0,1), planet = Moon)

        val movedRover = aRover.receiveCommand(Forwards)

        assertThat(movedRover.position).isEqualTo(Position(0, -1))
    }

    @Test
    fun `a Rover on the Planets' top edge, upon moving backwards while facing South, should be positioned at the Planets' bottom edge`() {
        val aRover = Rover(facingDirection = Direction.SOUTH, position = Position(0,1), planet = Moon)

        val movedRover = aRover.receiveCommand(Backwards)

        assertThat(movedRover.position).isEqualTo(Position(0, -1))
    }

    @Test
    fun `a Rover on the Planets' bottom edge, upon moving forwards while facing South, should be positioned at the Planets' top edge`() {
        val aRover = Rover(facingDirection = Direction.SOUTH, position = Position(0,-1), planet = Moon)

        val movedRover = aRover.receiveCommand(Forwards)

        assertThat(movedRover.position).isEqualTo(Position(0, 1))
    }

    @Test
    fun `a Rover on the Planets' bottom edge, upon moving backwards while facing North, should be positioned at the Planets' top edge`() {
        val aRover = Rover(facingDirection = Direction.NORTH, position = Position(0,-1), planet = Moon)

        val movedRover = aRover.receiveCommand(Backwards)

        assertThat(movedRover.position).isEqualTo(Position(0, 1))
    }

    @Test
    fun `a Rover on the Planets' right edge, upon moving forwards while facing East, should be positioned at the Planets' left edge`() {
        val aRover = Rover(facingDirection = Direction.EAST, position = Position(1,0), planet = Moon)

        val movedRover = aRover.receiveCommand(Forwards)

        assertThat(movedRover.position).isEqualTo(Position(-1, 0))
    }

    @Test
    fun `a Rover on the Planets' right edge, upon moving backwards while facing West, should be positioned at the Planets' left edge`() {
        val aRover = Rover(facingDirection = Direction.WEST, position = Position(1,0), planet = Moon)

        val movedRover = aRover.receiveCommand(Backwards)

        assertThat(movedRover.position).isEqualTo(Position(-1, 0))
    }

    @Test
    fun `a Rover on the Planets' left edge, upon moving forwards while facing West, should be positioned at the Planets' right edge`() {
        val aRover = Rover(facingDirection = Direction.WEST, position = Position(-1,0), planet = Moon)

        val movedRover = aRover.receiveCommand(Forwards)

        assertThat(movedRover.position).isEqualTo(Position(1, 0))
    }

    @Test
    fun `a Rover on the Planets' left edge, upon moving backwards while facing East, should be positioned at the Planets' right edge`() {
        val aRover = Rover(facingDirection = Direction.EAST, position = Position(-1,0), planet = Moon)

        val movedRover = aRover.receiveCommand(Backwards)

        assertThat(movedRover.position).isEqualTo(Position(1, 0))
    }

    @Test
    fun `a Rover on the planets' left edge, upon moving backwards while facing West, should not wrap Planets' edge`() {
        val aRover = Rover(facingDirection = Direction.WEST, position = Position(-1,0), planet = Moon)

        val movedRover = aRover.receiveCommand(Backwards)

        assertThat(movedRover.position).isEqualTo(Position(0, 0))
    }

    @Test
    fun `a Rover having an obstacle right in front of it, upon receiving Forwards, should report the obstacle's position`() {
        val aRover = Rover(planet = Mars(listOf(Position(0,1))))

        val actual = aRover.receiveCommand(Forwards)

        assertThat(actual.message).isEqualTo("There is an obstacle at (0,1), ignoring further commands.")
    }

    @Test
    fun `a Rover having an obstacle right behind it, upon receiving Backwards, should report the obstacle's position`() {
        val aRover = Rover(planet = Mars(listOf(Position(0,-1))))

        val actual = aRover.receiveCommand(Backwards)

        assertThat(actual.message).isEqualTo("There is an obstacle at (0,-1), ignoring further commands.")
    }

    @Test
    fun `a Rover having an obstacle right behind it, upon receiving Forwards, should not report the obstacle's position`() {
        val aRover = Rover(planet = Mars(listOf(Position(0,-1))))

        val actual = aRover.receiveCommand(Forwards)

        assertThat(actual.message).isNull()
    }

    @Test
    fun `a Rover having an obstacle right in front of it, upon receiving Backwards, should not report the obstacle's position`() {
        val aRover = Rover(planet = Mars(listOf(Position(0,1))))

        val actual = aRover.receiveCommand(Backwards)

        assertThat(actual.message).isNull()
    }

    @Test
    fun `a Rover having an obstacle right behind it, upon receiving a RotateCommand, should not report the obstacle's position`() {
        val aRover = Rover(facingDirection = Direction.EAST, planet = Mars(listOf(Position(0,1))))

        val actual = aRover.receiveCommand(Left)

        assertThat(actual.message).isNull()
    }
}