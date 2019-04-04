package be.swsb.coderetreat.rover

import assertk.assertThat
import assertk.assertions.isEqualTo
import be.swsb.coderetreat.planet.Dimension
import be.swsb.coderetreat.planet.Planet
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
        val aRover = Rover(planet = Planet.moon())

        val actual = aRover.receiveCommand(Left)

        assertThat(actual.planet).isEqualTo(Planet.moon())
    }

    @Test
    fun `a Rover on the moon, when moving, should stay on the moon`() {
        val aRover = Rover(planet = Planet.moon())

        val actual = aRover.receiveCommand(Forwards)

        assertThat(actual.planet).isEqualTo(Planet.moon())
    }

    @Test
    fun `a default Rover should be on the planet Mars`() {
        val defaultRover = Rover()

        assertThat(defaultRover.planet).isEqualTo(Planet.mars())
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
    fun `a Rover on the Planets' top edge, upon moving North, should be positioned at the Planets' bottom edge`() {
        val theMoon = Planet(Dimension(3,3))

        val aRover = Rover(facingDirection = Direction.NORTH, position = Position(0,1), planet = theMoon)

        val movedRover = aRover.receiveCommand(Forwards)

        assertThat(movedRover).isEqualTo(Rover(planet = theMoon, position = Position(0, -1)))
    }
}