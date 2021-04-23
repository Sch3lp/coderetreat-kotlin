package be.swsb.coderetreat

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


class RoverTest {

    @Test
    fun `A Rover can be initiated with a starting position and a starting facing direction`() {
        val initiatedRover = Rover()
        assertThat(initiatedRover).isEqualTo(Rover(at = Position(0, 0), facing = Direction.North))
    }

    @Nested
    inner class ReceivingForwards {
        @Test
        fun `A Rover facing North, moves up the Y axis`() {
            val initiatedRover = Rover().receive(Command.Forwards).receive(Command.Forwards)
            assertThat(initiatedRover).isEqualTo(Rover(at = Position(0, 2), facing = Direction.North))
        }

        @Test
        fun `A Rover facing South, moves down the Y axis`() {
            val initiatedRover = Rover(facing = Direction.South).receive(Command.Forwards).receive(Command.Forwards)
            assertThat(initiatedRover).isEqualTo(Rover(at = Position(0, -2), facing = Direction.South))
        }
    }
}

data class Rover(private val at: Position = Position(0, 0), private val facing: Direction = Direction.North) {
    fun receive(command: Command): Rover {
        val moveDirection = facing
        return this.copy(at = this.at.move(moveDirection))
    }
}

enum class Direction {
    North,
    South
}

enum class Command {
    Forwards
}

data class Position(private val x: Int, private val y: Int) {
    fun move(moveDirection: Direction): Position {
        return if (moveDirection == Direction.North) {
            moveUp()
        } else {
            moveDown()
        }
    }

    private fun moveUp(): Position {
        return this.copy(y = this.y + 1)
    }

    private fun moveDown(): Position {
        return this.copy(y = this.y - 1)
    }
}