package be.swsb.coderetreat

import be.swsb.coderetreat.FacingDirection.*
import be.swsb.coderetreat.MarsRoverCommand.MoveCommand
import be.swsb.coderetreat.MarsRoverCommand.RotateCommand.*
import be.swsb.coderetreat.MarsRoverCommand.MoveCommand.*
import be.swsb.coderetreat.MarsRoverCommand.RotateCommand
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


class PerseveranceTest {

    @Test
    fun `Perseverance lands on a position and a direction that it's facing towards`() {
        val landedPerseverance = land(Position(0, 0), North)

        landedPerseverance
            .receive(Left).also { assertThat(it).isEqualTo(Perseverance(Position(0, 0), West)) }
            .receive(Forwards).also { assertThat(it).isEqualTo(Perseverance(Position(-1, 0), West)) }
            .receive(Forwards).also { assertThat(it).isEqualTo(Perseverance(Position(-2, 0), West)) }
            .receive(Right).also { assertThat(it).isEqualTo(Perseverance(Position(-2, 0), North)) }
            .receive(Backwards).also { assertThat(it).isEqualTo(Perseverance(Position(-2, -1), North)) }
            .receive(Backwards).also { assertThat(it).isEqualTo(Perseverance(Position(-2, -2), North)) }
    }

    @Test
    fun `Perseverance can receive rotate and move commands and explore Mars`() {
        val landedPerseverance = land(Position(0, 0), North)

        assertThat(landedPerseverance)
            .isEqualTo(Perseverance(pos = Position(0, 0), facing = North))
    }

    @Nested
    inner class MovingForwards {

        @Test
        fun `Perseverance can receive a forwards command twice, while facing North and move up the Y axis`() {
            val landedPerseverance = land(Position(0, 0), North)

            val updatedRover: Perseverance = landedPerseverance
                .receive(Forwards)
                .receive(Forwards)

            assertThat(updatedRover)
                .isEqualTo(Perseverance(pos = Position(x = 0, y = 2), facing = North))
        }

        @Test
        fun `Perseverance can receive a forwards command twice, while facing South and move down the Y axis`() {
            val landedPerseverance = land(Position(0, 0), South)

            val updatedRover: Perseverance = landedPerseverance
                .receive(Forwards)
                .receive(Forwards)

            assertThat(updatedRover)
                .isEqualTo(Perseverance(pos = Position(x = 0, y = -2), facing = South))
        }

        @Test
        fun `Perseverance can receive a forwards command twice, while facing East and move up the X axis`() {
            val landedPerseverance = land(Position(0, 0), East)

            val updatedRover: Perseverance = landedPerseverance
                .receive(Forwards)
                .receive(Forwards)

            assertThat(updatedRover)
                .isEqualTo(Perseverance(pos = Position(x = 2, y = 0), facing = East))
        }

        @Test
        fun `Perseverance can receive a forwards command twice, while facing West and move down the X axis`() {
            val landedPerseverance = land(Position(0, 0), West)

            val updatedRover: Perseverance = landedPerseverance
                .receive(Forwards)
                .receive(Forwards)

            assertThat(updatedRover)
                .isEqualTo(Perseverance(pos = Position(x = -2, y = 0), facing = West))
        }
    }

    @Nested
    inner class MovingBackwards {

        @Test
        fun `Perseverance can receive a backwards command twice, while facing North and move down the Y axis`() {
            val landedPerseverance = land(Position(0, 0), North)

            val updatedRover: Perseverance = landedPerseverance
                .receive(Backwards)
                .receive(Backwards)

            assertThat(updatedRover)
                .isEqualTo(Perseverance(pos = Position(x = 0, y = -2), facing = North))
        }

        @Test
        fun `Perseverance can receive a backwards command twice, while facing South and move up the Y axis`() {
            val landedPerseverance = land(Position(0, 0), South)

            val updatedRover: Perseverance = landedPerseverance
                .receive(Backwards)
                .receive(Backwards)

            assertThat(updatedRover)
                .isEqualTo(Perseverance(pos = Position(x = 0, y = 2), facing = South))
        }

        @Test
        fun `Perseverance can receive a backwards command twice, while facing East and move down the X axis`() {
            val landedPerseverance = land(Position(0, 0), East)

            val updatedRover: Perseverance = landedPerseverance
                .receive(Backwards)
                .receive(Backwards)

            assertThat(updatedRover)
                .isEqualTo(Perseverance(pos = Position(x = -2, y = 0), facing = East))
        }

        @Test
        fun `Perseverance can receive a backwards command twice, while facing West and move up the X axis`() {
            val landedPerseverance = land(Position(0, 0), West)

            val updatedRover: Perseverance = landedPerseverance
                .receive(Backwards)
                .receive(Backwards)

            assertThat(updatedRover)
                .isEqualTo(Perseverance(pos = Position(x = 2, y = 0), facing = West))
        }
    }

    @Nested
    inner class Rotating {
        @Test
        internal fun `Perseverance can receive a left command, and change direction`() {
            land(Position(0, 0), North)
                .receive(Left).also { assertThat(it).isEqualTo(Perseverance(Position(0, 0), West)) }
                .receive(Left).also { assertThat(it).isEqualTo(Perseverance(Position(0, 0), South)) }
                .receive(Left).also { assertThat(it).isEqualTo(Perseverance(Position(0, 0), East)) }
                .receive(Left).also { assertThat(it).isEqualTo(Perseverance(Position(0, 0), North)) }
        }

        @Test
        internal fun `Perseverance can receive a right command, and change direction`() {
            land(Position(0, 0), North)
                .receive(Right).also { assertThat(it).isEqualTo(Perseverance(Position(0, 0), East)) }
                .receive(Right).also { assertThat(it).isEqualTo(Perseverance(Position(0, 0), South)) }
                .receive(Right).also { assertThat(it).isEqualTo(Perseverance(Position(0, 0), West)) }
                .receive(Right).also { assertThat(it).isEqualTo(Perseverance(Position(0, 0), North)) }
        }
    }
}

data class Perseverance(
    val pos: Position,
    val facing: FacingDirection,
) {
    fun receive(command: MarsRoverCommand) = when (command) {
        is MoveCommand -> move(command)
        is RotateCommand -> rotate(command)
    }

    private fun move(cmd: MoveCommand) = when(cmd) {
        Forwards -> moveForwards()
        Backwards -> moveBackwards()
    }

    private fun moveForwards() = when (facing) {
        North -> copy(pos = pos.moveUpTheYAxis(1))
        South -> copy(pos = pos.moveDownTheYAxis(1))
        East -> copy(pos = pos.moveUpTheXAxis(1))
        West -> copy(pos = pos.moveDownTheXAxis(1))
    }
    private fun moveBackwards() = when (facing) {
        North -> copy(pos = pos.moveDownTheYAxis(1))
        South -> copy(pos = pos.moveUpTheYAxis(1))
        East -> copy(pos = pos.moveDownTheXAxis(1))
        West -> copy(pos = pos.moveUpTheXAxis(1))
    }

    private fun rotate(rotateCommand: RotateCommand) = when (rotateCommand) {
        Right -> copy(facing = facing.clockwise())
        Left -> copy(facing = facing.counterClockwise())
    }

}

data class Position(private val x: Int, private val y: Int) {
    fun moveUpTheYAxis(step: Int) = this.copy(y = this.y + step)
    fun moveDownTheYAxis(step: Int) = this.copy(y = this.y - step)
    fun moveUpTheXAxis(step: Int) = this.copy(x = this.x + step)
    fun moveDownTheXAxis(step: Int) = this.copy(x = this.x - step)
}


sealed class MarsRoverCommand {
    sealed class MoveCommand : MarsRoverCommand() {
        object Forwards: MoveCommand()
        object Backwards: MoveCommand()
    }
    sealed class RotateCommand : MarsRoverCommand() {
        object Left: RotateCommand()
        object Right: RotateCommand()
    }
}

enum class FacingDirection {
    North,
    East,
    South,
    West,
    ;

    fun clockwise() = when (this) {
        North -> East
        East -> South
        South -> West
        West -> North
    }

    fun counterClockwise() = when (this) {
        North -> West
        East -> North
        South -> East
        West -> South
    }
}

fun land(landingPosition: Position, facingDirection: FacingDirection): Perseverance {
    return Perseverance(pos = landingPosition, facing = facingDirection)
}
