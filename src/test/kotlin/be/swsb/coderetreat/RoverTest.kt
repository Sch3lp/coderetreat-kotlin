package be.swsb.coderetreat

import be.swsb.coderetreat.Command.MoveCommand.Backwards
import be.swsb.coderetreat.Command.MoveCommand.Forwards
import be.swsb.coderetreat.Command.RotateCommand.Left
import be.swsb.coderetreat.Command.RotateCommand.Right
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
            val initiatedRover = Rover().receive(Forwards).receive(Forwards)
            assertThat(initiatedRover).isEqualTo(Rover(at = Position(0, 2), facing = Direction.North))
        }

        @Test
        fun `A Rover facing South, moves down the Y axis`() {
            val initiatedRover = Rover(facing = Direction.South).receive(Forwards)
                .receive(Forwards)
            assertThat(initiatedRover).isEqualTo(Rover(at = Position(0, -2), facing = Direction.South))
        }

        @Test
        fun `A Rover facing West, moves down the X axis`() {
            val initiatedRover = Rover(facing = Direction.West).receive(Forwards)
                .receive(Forwards)
            assertThat(initiatedRover).isEqualTo(Rover(at = Position(-2, 0), facing = Direction.West))
        }

        @Test
        fun `A Rover facing East, moves up the X axis`() {
            val initiatedRover = Rover(facing = Direction.East).receive(Forwards)
                .receive(Forwards)
            assertThat(initiatedRover).isEqualTo(Rover(at = Position(2, 0), facing = Direction.East))
        }
    }

    @Nested
    inner class ReceivingBackwards {
        @Test
        fun `A Rover facing North, moves down the Y axis`() {
            val initiatedRover = Rover().receive(Backwards).receive(Backwards)
            assertThat(initiatedRover).isEqualTo(Rover(at = Position(0, -2), facing = Direction.North))
        }

        @Test
        fun `A Rover facing South, moves up the Y axis`() {
            val initiatedRover = Rover(facing = Direction.South).receive(Backwards)
                .receive(Backwards)
            assertThat(initiatedRover).isEqualTo(Rover(at = Position(0, 2), facing = Direction.South))
        }

        @Test
        fun `A Rover facing West, moves up the X axis`() {
            val initiatedRover = Rover(facing = Direction.West).receive(Backwards)
                .receive(Backwards)
            assertThat(initiatedRover).isEqualTo(Rover(at = Position(2, 0), facing = Direction.West))
        }

        @Test
        fun `A Rover facing East, moves down the X axis`() {
            val initiatedRover = Rover(facing = Direction.East).receive(Backwards)
                .receive(Backwards)
            assertThat(initiatedRover).isEqualTo(Rover(at = Position(-2, 0), facing = Direction.East))
        }
    }

    @Nested
    inner class ReceivingRight {
        @Test
        fun `A Rover rotates clockwise`() {
            assertThat(Rover(facing = Direction.North).receive(Right)).isEqualTo(Rover(facing = Direction.East))
            assertThat(Rover(facing = Direction.East).receive(Right)).isEqualTo(Rover(facing = Direction.South))
            assertThat(Rover(facing = Direction.South).receive(Right)).isEqualTo(Rover(facing = Direction.West))
            assertThat(Rover(facing = Direction.West).receive(Right)).isEqualTo(Rover(facing = Direction.North))
        }
    }

    @Nested
    inner class ReceivingLeft {
        @Test
        fun `A Rover rotates counter-clockwise`() {
            assertThat(Rover(facing = Direction.North).receive(Left)).isEqualTo(Rover(facing = Direction.West))
            assertThat(Rover(facing = Direction.West).receive(Left)).isEqualTo(Rover(facing = Direction.South))
            assertThat(Rover(facing = Direction.South).receive(Left)).isEqualTo(Rover(facing = Direction.East))
            assertThat(Rover(facing = Direction.East).receive(Left)).isEqualTo(Rover(facing = Direction.North))
        }
    }
}

data class Rover(
    private val at: Position = Position(0, 0),
    private val facing: Direction = Direction.North
) {
    fun receive(command: Command): Rover {
        return when (command) {
            is Command.RotateCommand -> rotate(command)
            is Command.MoveCommand -> move(command)
        }
    }

    private fun move(cmd: Command.MoveCommand) = when (cmd) {
        Forwards -> this.copy(at = this.at.move(facing))
        Backwards -> this.copy(at = this.at.move(facing.opposite()))
    }

    private fun rotate(cmd: Command.RotateCommand) = when (cmd) {
        Right -> this.copy(facing = facing.rotateClockwise())
        Left -> this.copy(facing = facing.rotateCounterClockwise())
    }
}

enum class Direction {
    North,
    South,
    West,
    East;

    fun opposite() = when (this) {
        North -> South
        South -> North
        West -> East
        East -> West
    }

    fun rotateClockwise() = when (this) {
        North -> East
        East -> South
        South -> West
        West -> North
    }

    fun rotateCounterClockwise() = rotateClockwise().opposite()
}

sealed class Command {
    sealed class MoveCommand : Command() {
        object Forwards : MoveCommand()
        object Backwards : MoveCommand()
    }

    sealed class RotateCommand : Command() {
        object Right : RotateCommand()
        object Left : RotateCommand()
    }
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