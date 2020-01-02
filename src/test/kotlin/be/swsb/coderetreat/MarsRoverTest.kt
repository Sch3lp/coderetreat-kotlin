package be.swsb.coderetreat

import be.swsb.coderetreat.Direction.*
import be.swsb.coderetreat.Position.Companion.at
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class MarsRoverTest {

    @Test
    fun `a rover has a default position`() {
        //given
        val rover = Rover()

        //when
        val position = rover.position

        //then
        assertThat(position).isEqualTo(at(0, 0))
    }

    @Test
    fun `a rover has a default facing direction`() {
        //given
        val rover = Rover()

        //when
        val direction = rover.direction

        //then
        assertThat(direction).isEqualTo(NORTH)
    }

    @Test
    fun `when left received, turns rover counterclockwise`() {
        assertThat(Rover(direction = NORTH).receiveLeft()).isEqualTo(Rover(direction = WEST))
        assertThat(Rover(direction = WEST).receiveLeft()).isEqualTo(Rover(direction = SOUTH))
        assertThat(Rover(direction = SOUTH).receiveLeft()).isEqualTo(Rover(direction = EAST))
        assertThat(Rover(direction = EAST).receiveLeft()).isEqualTo(Rover(direction = NORTH))
    }

    @Test
    fun `when right received, turns rover clockwise`() {
        assertThat(Rover(direction = NORTH).receiveRight()).isEqualTo(Rover(direction = EAST))
        assertThat(Rover(direction = EAST).receiveRight()).isEqualTo(Rover(direction = SOUTH))
        assertThat(Rover(direction = SOUTH).receiveRight()).isEqualTo(Rover(direction = WEST))
        assertThat(Rover(direction = WEST).receiveRight()).isEqualTo(Rover(direction = NORTH))
    }

    @Test
    fun `when forward received when facing north, move rover forward one position on y-axis`() {
        //given
        val rover = Rover()

        //when
        val actual = rover.receiveForward()

        //then
        assertThat(actual).isEqualTo(Rover(position = at(0, 1)))
    }

    @Test
    fun `when forward received twice when facing north, move rover forward two positions on the y-axis`() {
        //given
        val rover = Rover()

        //when
        val actual = rover
                .receiveForward()
                .receiveForward()

        //then
        assertThat(actual).isEqualTo(Rover(position = at(0, 2)))
    }

    @Test
    fun `when moving forward twice, move forward in correct direction`() {
        assertThat(Rover(direction = NORTH).receiveForward().receiveForward()).isEqualTo(Rover(at(0, 2), NORTH))
        assertThat(Rover(direction = EAST).receiveForward().receiveForward()).isEqualTo(Rover(at(2, 0), EAST))
        assertThat(Rover(direction = SOUTH).receiveForward().receiveForward()).isEqualTo(Rover(at(0, -2), SOUTH))
        assertThat(Rover(direction = WEST).receiveForward().receiveForward()).isEqualTo(Rover(at(-2, 0), WEST))
    }

    @Test
    fun `when moving backward twice, move backward in correct direction`() {
        assertThat(Rover(direction = NORTH).receiveBackward().receiveBackward()).isEqualTo(Rover(at(0, -2), NORTH))
        assertThat(Rover(direction = EAST).receiveBackward().receiveBackward()).isEqualTo(Rover(at(-2, 0), EAST))
        assertThat(Rover(direction = SOUTH).receiveBackward().receiveBackward()).isEqualTo(Rover(at(0, 2), SOUTH))
        assertThat(Rover(direction = WEST).receiveBackward().receiveBackward()).isEqualTo(Rover(at(2, 0), WEST))
    }

}

data class Rover(val position: Position = at(0, 0), val direction: Direction = NORTH) {

    fun receiveLeft() = this.copy(direction = direction.turnLeft())
    fun receiveRight() = this.copy(direction = direction.turnRight())
    fun receiveForward() = move(direction.applyForward())
    fun receiveBackward() = move(direction.applyBackward())

    private fun move(applyMovement: Position.() -> Position): Rover {
        return this.copy(position = position.applyMovement())
    }
}

data class Position(val x: Int, val y: Int) {
    fun moveUpOnY(): Position = copy(y = y + 1)
    fun moveDownOnY(): Position = copy(y = y - 1)
    fun moveUpOnX(): Position = copy(x = x + 1)
    fun moveDownOnX(): Position = copy(x = x - 1)

    companion object {
        fun at(x: Int, y: Int) = Position(x, y)
    }

}

enum class Direction {
    NORTH, EAST, SOUTH, WEST;

    fun turnLeft(): Direction =
            when (this) {
                NORTH -> WEST
                WEST -> SOUTH
                SOUTH -> EAST
                EAST -> NORTH
            }

    fun turnRight(): Direction =
            when (this) {
                NORTH -> EAST
                EAST -> SOUTH
                SOUTH -> WEST
                WEST -> NORTH
            }

    fun applyForward(): Position.() -> Position {
        return when (this) {
            NORTH -> Position::moveUpOnY
            EAST -> Position::moveUpOnX
            SOUTH -> Position::moveDownOnY
            WEST -> Position::moveDownOnX
        }
    }

    fun applyBackward(): Position.() -> Position {
        return when (this) {
            NORTH -> Position::moveDownOnY
            EAST -> Position::moveDownOnX
            SOUTH -> Position::moveUpOnY
            WEST -> Position::moveUpOnX
        }
    }
}
