package be.swsb.coderetreat

import assertk.assertThat
import assertk.assertions.isEqualTo
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
    fun aRoverFacingNorth_UponReceivingForwards_ShouldMove1PositionNorth() {
        val aRover = Rover(facingDirection = Direction.NORTH)

        val movedRover = aRover.receiveCommand(Command.FORWARDS)

        assertThat(movedRover).isEqualTo(Rover(position = Position(0, 1), facingDirection = Direction.NORTH))
    }

    @Test
    fun aRoverFacingEast_UponReceivingForwards_ShouldMove1PositionEast() {
        val aRover = Rover(facingDirection = Direction.EAST)

        val movedRover = aRover.receiveCommand(Command.FORWARDS)

        assertThat(movedRover).isEqualTo(Rover(position = Position(1, 0), facingDirection = Direction.EAST))
    }

    @Test
    fun aRoverFacingSouth_UponReceivingForwards_ShouldMove1PositionSouth() {
        val aRover = Rover(facingDirection = Direction.SOUTH)

        val movedRover = aRover.receiveCommand(Command.FORWARDS)

        assertThat(movedRover).isEqualTo(Rover(position = Position(0, -1), facingDirection = Direction.SOUTH))
    }

    @Test
    fun aRoverFacingWest_UponReceivingForwards_ShouldMove1PositionWest() {
        val aRover = Rover(facingDirection = Direction.WEST)

        val movedRover = aRover.receiveCommand(Command.FORWARDS)

        assertThat(movedRover).isEqualTo(Rover(position = Position(-1, 0), facingDirection = Direction.WEST))
    }

    @Test
    fun aRoverFacingNorth_UponReceivingForwardsTwice_ShouldMove2PositionsNorth() {
        val aRover = Rover(facingDirection = Direction.NORTH)

        val movedRover = aRover.receiveCommand(Command.FORWARDS).receiveCommand(Command.FORWARDS)

        assertThat(movedRover).isEqualTo(Rover(position = Position(0, 2), facingDirection = Direction.NORTH))
    }

    @Test
    fun aRoverFacingEast_UponReceivingForwardsTwice_ShouldMove2PositionsEast() {
        val aRover = Rover(facingDirection = Direction.EAST)

        val movedRover = aRover.receiveCommand(Command.FORWARDS).receiveCommand(Command.FORWARDS)

        assertThat(movedRover).isEqualTo(Rover(position = Position(2, 0), facingDirection = Direction.EAST))
    }

    @Test
    fun aRoverFacingSouth_UponReceivingForwardsTwice_ShouldMove2PositionsSouth() {
        val aRover = Rover(facingDirection = Direction.SOUTH)

        val movedRover = aRover.receiveCommand(Command.FORWARDS).receiveCommand(Command.FORWARDS)

        assertThat(movedRover).isEqualTo(Rover(position = Position(0, -2), facingDirection = Direction.SOUTH))
    }

    @Test
    fun aRoverFacingWest_UponReceivingForwardsTwice_ShouldMove2PositionsWest() {
        val aRover = Rover(facingDirection = Direction.WEST)

        val movedRover = aRover.receiveCommand(Command.FORWARDS).receiveCommand(Command.FORWARDS)

        assertThat(movedRover).isEqualTo(Rover(position = Position(-2, 0), facingDirection = Direction.WEST))
    }

    @Test
    fun aRoverFacingNorth_UponReceivingBackwards_ShouldMove1PositionSouth() {
        val aRover = Rover(facingDirection = Direction.NORTH)

        val movedRover = aRover.receiveCommand(Command.BACKWARDS)

        assertThat(movedRover).isEqualTo(Rover(position = Position(0, -1), facingDirection = Direction.NORTH))
    }

    @Test
    fun aRoverFacingEast_UponReceivingBackwards_ShouldMove1PositionWest() {
        val aRover = Rover(facingDirection = Direction.EAST)

        val movedRover = aRover.receiveCommand(Command.BACKWARDS)

        assertThat(movedRover).isEqualTo(Rover(position = Position(-1, 0), facingDirection = Direction.EAST))
    }

    @Test
    fun aRoverFacingSouth_UponReceivingBackwards_ShouldMove1PositionNorth() {
        val aRover = Rover(facingDirection = Direction.SOUTH)

        val movedRover = aRover.receiveCommand(Command.BACKWARDS)

        assertThat(movedRover).isEqualTo(Rover(position = Position(0, 1), facingDirection = Direction.SOUTH))
    }

    @Test
    fun aRoverFacingWest_UponReceivingBackwards_ShouldMove1PositionEast() {
        val aRover = Rover(facingDirection = Direction.WEST)

        val movedRover = aRover.receiveCommand(Command.BACKWARDS)

        assertThat(movedRover).isEqualTo(Rover(position = Position(1, 0), facingDirection = Direction.WEST))
    }

    @Test
    fun aRoverFacingNorth_UponReceivingBackwardsTwice_ShouldMove2PositionsSouth() {
        val aRover = Rover(facingDirection = Direction.NORTH)

        val movedRover = aRover.receiveCommand(Command.BACKWARDS).receiveCommand(Command.BACKWARDS)

        assertThat(movedRover).isEqualTo(Rover(position = Position(0, -2), facingDirection = Direction.NORTH))
    }

    @Test
    fun aRoverFacingEast_UponReceivingBackwardsTwice_ShouldMove2PositionsWest() {
        val aRover = Rover(facingDirection = Direction.EAST)

        val movedRover = aRover.receiveCommand(Command.BACKWARDS).receiveCommand(Command.BACKWARDS)

        assertThat(movedRover).isEqualTo(Rover(position = Position(-2, 0), facingDirection = Direction.EAST))
    }

    @Test
    fun aRoverFacingSouth_UponReceivingBackwardsTwice_ShouldMove2PositionsNorth() {
        val aRover = Rover(facingDirection = Direction.SOUTH)

        val movedRover = aRover.receiveCommand(Command.BACKWARDS).receiveCommand(Command.BACKWARDS)

        assertThat(movedRover).isEqualTo(Rover(position = Position(0, 2), facingDirection = Direction.SOUTH))
    }

    @Test
    fun aRoverFacingWest_UponReceivingBackwardsTwice_ShouldMove2PositionsEast() {
        val aRover = Rover(facingDirection = Direction.WEST)

        val movedRover = aRover.receiveCommand(Command.BACKWARDS).receiveCommand(Command.BACKWARDS)

        assertThat(movedRover).isEqualTo(Rover(position = Position(2, 0), facingDirection = Direction.WEST))
    }
}