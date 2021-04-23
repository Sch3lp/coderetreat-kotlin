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

        @Test
        fun `A Rover facing West, moves down the X axis`() {
            val initiatedRover = Rover(facing = Direction.West).receive(Command.Forwards).receive(Command.Forwards)
            assertThat(initiatedRover).isEqualTo(Rover(at = Position(-2, 0), facing = Direction.West))
        }

        @Test
        fun `A Rover facing East, moves up the X axis`() {
            val initiatedRover = Rover(facing = Direction.East).receive(Command.Forwards).receive(Command.Forwards)
            assertThat(initiatedRover).isEqualTo(Rover(at = Position(2, 0), facing = Direction.East))
        }
    }

    @Nested
    inner class ReceivingBackwards {
        @Test
        fun `A Rover facing North, moves down the Y axis`() {
            val initiatedRover = Rover().receive(Command.Backwards).receive(Command.Backwards)
            assertThat(initiatedRover).isEqualTo(Rover(at = Position(0, -2), facing = Direction.North))
        }

        @Test
        fun `A Rover facing South, moves up the Y axis`() {
            val initiatedRover = Rover(facing = Direction.South).receive(Command.Backwards).receive(Command.Backwards)
            assertThat(initiatedRover).isEqualTo(Rover(at = Position(0, 2), facing = Direction.South))
        }

        @Test
        fun `A Rover facing West, moves up the X axis`() {
            val initiatedRover = Rover(facing = Direction.West).receive(Command.Backwards).receive(Command.Backwards)
            assertThat(initiatedRover).isEqualTo(Rover(at = Position(2, 0), facing = Direction.West))
        }

        @Test
        fun `A Rover facing East, moves down the X axis`() {
            val initiatedRover = Rover(facing = Direction.East).receive(Command.Backwards).receive(Command.Backwards)
            assertThat(initiatedRover).isEqualTo(Rover(at = Position(-2, 0), facing = Direction.East))
        }
    }
}

data class Rover(private val at: Position = Position(0, 0), private val facing: Direction = Direction.North) {
    fun receive(command: Command): Rover {
        val moveDirection = if (command == Command.Backwards) facing.opposite() else facing
        return this.copy(at = this.at.move(moveDirection))
    }
}

enum class Direction {
    North,
    South,
    West,
    East;

    fun opposite(): Direction = when (this) {
        North -> South
        South -> North
        West -> East
        East -> West
    }
}

enum class Command {
    Forwards,
    Backwards
}

data class Position(private val x: Int, private val y: Int) {
    fun move(moveDirection: Direction) = when (moveDirection) {
        Direction.North -> moveUp()
        Direction.South -> moveDown()
        Direction.West -> moveLeft()
        Direction.East -> moveRight()
    }

    private fun moveUp(): Position {
        return this.copy(y = this.y + 1)
    }

    private fun moveDown(): Position {
        return this.copy(y = this.y - 1)
    }

    private fun moveLeft(): Position {
        return this.copy(x = this.x - 1)
    }

    private fun moveRight(): Position {
        return this.copy(x = this.x + 1)
    }
}