package be.swsb.coderetreat.rover

import assertk.assertThat
import assertk.assertions.isEqualTo
import be.swsb.coderetreat.planet.Dimension
import be.swsb.coderetreat.planet.Planet
import org.junit.Test

class RoverTest {

    @Test
    fun aDefaultRoverShouldBeFacingNorth() {
        val defaultRover = Rover()

        assertThat(defaultRover.facingDirection).isEqualTo(Direction.NORTH)
    }

    @Test
    fun aDefaultRoverShouldBePositionedAt00() {
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
    fun aDefaultRoverShouldBeOnThePlanetMars() {
        val defaultRover = Rover()

        assertThat(defaultRover.planet).isEqualTo(Planet.mars())
    }

    @Test
    fun aRoverFacingNorth_UponReceivingForwards_ShouldMove1PositionNorth() {
        val aRover = Rover(facingDirection = Direction.NORTH)

        val movedRover = aRover.receiveCommand(Forwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(0, 1), facingDirection = Direction.NORTH))
    }

    @Test
    fun aRoverFacingEast_UponReceivingForwards_ShouldMove1PositionEast() {
        val aRover = Rover(facingDirection = Direction.EAST)

        val movedRover = aRover.receiveCommand(Forwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(1, 0), facingDirection = Direction.EAST))
    }

    @Test
    fun aRoverFacingSouth_UponReceivingForwards_ShouldMove1PositionSouth() {
        val aRover = Rover(facingDirection = Direction.SOUTH)

        val movedRover = aRover.receiveCommand(Forwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(0, -1), facingDirection = Direction.SOUTH))
    }

    @Test
    fun aRoverFacingWest_UponReceivingForwards_ShouldMove1PositionWest() {
        val aRover = Rover(facingDirection = Direction.WEST)

        val movedRover = aRover.receiveCommand(Forwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(-1, 0), facingDirection = Direction.WEST))
    }

    @Test
    fun aRoverFacingNorth_UponReceivingForwardsTwice_ShouldMove2PositionsNorth() {
        val aRover = Rover(facingDirection = Direction.NORTH)

        val movedRover = aRover.receiveCommand(Forwards).receiveCommand(Forwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(0, 2), facingDirection = Direction.NORTH))
    }

    @Test
    fun aRoverFacingEast_UponReceivingForwardsTwice_ShouldMove2PositionsEast() {
        val aRover = Rover(facingDirection = Direction.EAST)

        val movedRover = aRover.receiveCommand(Forwards).receiveCommand(Forwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(2, 0), facingDirection = Direction.EAST))
    }

    @Test
    fun aRoverFacingSouth_UponReceivingForwardsTwice_ShouldMove2PositionsSouth() {
        val aRover = Rover(facingDirection = Direction.SOUTH)

        val movedRover = aRover.receiveCommand(Forwards).receiveCommand(Forwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(0, -2), facingDirection = Direction.SOUTH))
    }

    @Test
    fun aRoverFacingWest_UponReceivingForwardsTwice_ShouldMove2PositionsWest() {
        val aRover = Rover(facingDirection = Direction.WEST)

        val movedRover = aRover.receiveCommand(Forwards).receiveCommand(Forwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(-2, 0), facingDirection = Direction.WEST))
    }

    @Test
    fun aRoverFacingNorth_UponReceivingBackwards_ShouldMove1PositionSouth() {
        val aRover = Rover(facingDirection = Direction.NORTH)

        val movedRover = aRover.receiveCommand(Backwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(0, -1), facingDirection = Direction.NORTH))
    }

    @Test
    fun aRoverFacingEast_UponReceivingBackwards_ShouldMove1PositionWest() {
        val aRover = Rover(facingDirection = Direction.EAST)

        val movedRover = aRover.receiveCommand(Backwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(-1, 0), facingDirection = Direction.EAST))
    }

    @Test
    fun aRoverFacingSouth_UponReceivingBackwards_ShouldMove1PositionNorth() {
        val aRover = Rover(facingDirection = Direction.SOUTH)

        val movedRover = aRover.receiveCommand(Backwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(0, 1), facingDirection = Direction.SOUTH))
    }

    @Test
    fun aRoverFacingWest_UponReceivingBackwards_ShouldMove1PositionEast() {
        val aRover = Rover(facingDirection = Direction.WEST)

        val movedRover = aRover.receiveCommand(Backwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(1, 0), facingDirection = Direction.WEST))
    }

    @Test
    fun aRoverFacingNorth_UponReceivingBackwardsTwice_ShouldMove2PositionsSouth() {
        val aRover = Rover(facingDirection = Direction.NORTH)

        val movedRover = aRover.receiveCommand(Backwards).receiveCommand(Backwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(0, -2), facingDirection = Direction.NORTH))
    }

    @Test
    fun aRoverFacingEast_UponReceivingBackwardsTwice_ShouldMove2PositionsWest() {
        val aRover = Rover(facingDirection = Direction.EAST)

        val movedRover = aRover.receiveCommand(Backwards).receiveCommand(Backwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(-2, 0), facingDirection = Direction.EAST))
    }

    @Test
    fun aRoverFacingSouth_UponReceivingBackwardsTwice_ShouldMove2PositionsNorth() {
        val aRover = Rover(facingDirection = Direction.SOUTH)

        val movedRover = aRover.receiveCommand(Backwards).receiveCommand(Backwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(0, 2), facingDirection = Direction.SOUTH))
    }

    @Test
    fun aRoverFacingWest_UponReceivingBackwardsTwice_ShouldMove2PositionsEast() {
        val aRover = Rover(facingDirection = Direction.WEST)

        val movedRover = aRover.receiveCommand(Backwards).receiveCommand(Backwards)

        assertThat(movedRover).isEqualTo(Rover(position = Position(2, 0), facingDirection = Direction.WEST))
    }

    @Test
    fun aRoverFacingNorth_UponReceivingRight_RotatesToEast() {
        val aRover = Rover(facingDirection = Direction.NORTH)

        val rotatedRover = aRover.receiveCommand(Right)

        assertThat(rotatedRover).isEqualTo(Rover(facingDirection = Direction.EAST, position = aRover.position))
    }

    @Test
    fun aRoverFacingNorth_UponReceivingRight4Times_RotatesBackToNorth() {
        val aRover = Rover(facingDirection = Direction.NORTH)

        val rotatedRover = aRover.receiveCommand(Right).receiveCommand(Right).receiveCommand(Right).receiveCommand(Right)

        assertThat(rotatedRover).isEqualTo(Rover(facingDirection = Direction.NORTH, position = aRover.position))
    }

    @Test
    fun aRoverFacingNorth_UponReceivingLeft4Times_RotatesBackToNorth() {
        val aRover = Rover(facingDirection = Direction.NORTH)

        val rotatedRover = aRover.receiveCommand(Left).receiveCommand(Left).receiveCommand(Left).receiveCommand(Left)

        assertThat(rotatedRover).isEqualTo(Rover(facingDirection = Direction.NORTH, position = aRover.position))
    }

    @Test
    fun aRoverOnThePlanetsTopEdge_UponMovingNorth_ShouldBePositionedAtThePlanetsBottomEdge() {
        val theMoon = Planet(Dimension(3,3))

        val aRover = Rover(facingDirection = Direction.NORTH, position = Position(0,1), planet = theMoon)

        val movedRover = aRover.receiveCommand(Forwards)

        assertThat(movedRover).isEqualTo(Rover(planet = theMoon, position = Position(0, -1)))
    }
}