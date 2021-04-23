package be.swsb.coderetreat

import be.swsb.coderetreat.Command.MoveCommand.Backwards
import be.swsb.coderetreat.Command.MoveCommand.Forwards
import be.swsb.coderetreat.Command.RotateCommand.Left
import be.swsb.coderetreat.Command.RotateCommand.Right
import be.swsb.coderetreat.Direction.*
import be.swsb.coderetreat.Position.Companion.at
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


class RoverTest {

    @Test
    fun `A Rover can be initiated with a starting position and a starting facing direction`() {
        val initiatedRover = Rover()
        assertThat(initiatedRover).isEqualTo(Rover(at = at(0, 0), facing = North))
    }

    @Nested
    inner class ReceivingForwards {
        @Test
        fun `A Rover facing North, moves up the Y axis`() {
            val initiatedRover = Rover().receive(Forwards).receive(Forwards)
            assertThat(initiatedRover).isEqualTo(Rover(at = at(0, 2), facing = North))
        }

        @Test
        fun `A Rover facing South, moves down the Y axis`() {
            val initiatedRover = Rover(facing = South).receive(Forwards)
                .receive(Forwards)
            assertThat(initiatedRover).isEqualTo(Rover(at = at(0, -2), facing = South))
        }

        @Test
        fun `A Rover facing West, moves down the X axis`() {
            val initiatedRover = Rover(facing = West).receive(Forwards)
                .receive(Forwards)
            assertThat(initiatedRover).isEqualTo(Rover(at = at(-2, 0), facing = West))
        }

        @Test
        fun `A Rover facing East, moves up the X axis`() {
            val initiatedRover = Rover(facing = East).receive(Forwards)
                .receive(Forwards)
            assertThat(initiatedRover).isEqualTo(Rover(at = at(2, 0), facing = East))
        }
    }

    @Nested
    inner class ReceivingBackwards {
        @Test
        fun `A Rover facing North, moves down the Y axis`() {
            val initiatedRover = Rover().receive(Backwards).receive(Backwards)
            assertThat(initiatedRover).isEqualTo(Rover(at = at(0, -2), facing = North))
        }

        @Test
        fun `A Rover facing South, moves up the Y axis`() {
            val initiatedRover = Rover(facing = South).receive(Backwards)
                .receive(Backwards)
            assertThat(initiatedRover).isEqualTo(Rover(at = at(0, 2), facing = South))
        }

        @Test
        fun `A Rover facing West, moves up the X axis`() {
            val initiatedRover = Rover(facing = West).receive(Backwards)
                .receive(Backwards)
            assertThat(initiatedRover).isEqualTo(Rover(at = at(2, 0), facing = West))
        }

        @Test
        fun `A Rover facing East, moves down the X axis`() {
            val initiatedRover = Rover(facing = East).receive(Backwards)
                .receive(Backwards)
            assertThat(initiatedRover).isEqualTo(Rover(at = at(-2, 0), facing = East))
        }
    }

    @Nested
    inner class ReceivingRight {
        @Test
        fun `A Rover rotates clockwise`() {
            assertThat(Rover(facing = North).receive(Right)).isEqualTo(Rover(facing = East))
            assertThat(Rover(facing = East).receive(Right)).isEqualTo(Rover(facing = South))
            assertThat(Rover(facing = South).receive(Right)).isEqualTo(Rover(facing = West))
            assertThat(Rover(facing = West).receive(Right)).isEqualTo(Rover(facing = North))
        }
    }

    @Nested
    inner class ReceivingLeft {
        @Test
        fun `A Rover rotates counter-clockwise`() {
            assertThat(Rover(facing = North).receive(Left)).isEqualTo(Rover(facing = West))
            assertThat(Rover(facing = West).receive(Left)).isEqualTo(Rover(facing = South))
            assertThat(Rover(facing = South).receive(Left)).isEqualTo(Rover(facing = East))
            assertThat(Rover(facing = East).receive(Left)).isEqualTo(Rover(facing = North))
        }
    }

    @Test
    fun `Some fun Scenario`() {
        Rover()
            .receive(Forwards).also { assertThat(it).isEqualTo(Rover(at = at(0,1))) }
            .receive(Forwards).also { assertThat(it).isEqualTo(Rover(at = at(0,2))) }
            .receive(Right).also { assertThat(it).isEqualTo(Rover(at = at(0,2), facing = East)) }
            .receive(Forwards).also { assertThat(it).isEqualTo(Rover(at = at(1,2), facing = East)) }
            .receive(Right).also { assertThat(it).isEqualTo(Rover(at = at(1,2), facing = South)) }
            .receive(Backwards).also { assertThat(it).isEqualTo(Rover(at = at(1,3), facing = South)) }
            .receive(Left).also { assertThat(it).isEqualTo(Rover(at = at(1,3), facing = East)) }
    }

    @Test
    fun `Receiving multiple commands`() {
        val updatedRover = Rover().receive(listOf(Forwards, Forwards, Right, Forwards, Right, Backwards, Left))
        assertThat(updatedRover).isEqualTo(Rover(at = at(1,3), facing = East))
    }

}

data class Rover(
    private val at: Position = at(0, 0),
    private val facing: Direction = North
) {

    fun receive(commands: List<Command>) = commands.fold(this) { acc, cmd -> acc.receive(cmd) }

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


data class Position private constructor(private val x: Int, private val y: Int) {
    fun move(moveDirection: Direction) = when (moveDirection) {
        North -> moveUp()
        South -> moveDown()
        West -> moveLeft()
        East -> moveRight()
    }

    private fun moveUp() = this.copy(y = this.y + 1)
    private fun moveDown() = this.copy(y = this.y - 1)
    private fun moveLeft() = this.copy(x = this.x - 1)
    private fun moveRight() = this.copy(x = this.x + 1)

    companion object {
        fun at(x: Int, y: Int) = Position(x,y)
    }
}