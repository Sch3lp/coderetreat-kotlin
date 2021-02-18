package be.swsb.coderetreat

import be.swsb.coderetreat.FacingDirection.*
import be.swsb.coderetreat.MarsRoverCommand.Forwards
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class PerseveranceTest {

    @Test
    fun `Perseverance lands on a position and a direction that it's facing towards`() {
        val landedPerseverance = land(Position(0, 0), North)

        assertThat(landedPerseverance)
            .isEqualTo(Perseverance(pos = Position(0, 0), facing = North))
    }

    @Test
    fun `Perseverance can receive a forwards command and move towards a new position`() {
        val landedPerseverance = land(Position(0, 0), North)

        val updatedRover = landedPerseverance.receive(Forwards)

        assertThat(updatedRover)
            .isEqualTo(Perseverance(pos = Position(x = 0, y = 1), facing = North))
    }

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

    }

}

data class Perseverance(
    val pos: Position,
    val facing: FacingDirection,
) {
    fun receive(command: MarsRoverCommand): Perseverance {
        return when (facing) {
            North -> {
                copy(pos = moveUpTheYAxis())
            }
            South -> {
                copy(pos = moveDownTheYAxis())
            }
            East -> {
                copy(pos = moveUpTheXAxis())
            }
            else -> this
        }
    }

    private fun moveUpTheYAxis() = this.pos.moveUpTheYAxis(1)
    private fun moveDownTheYAxis() = this.pos.moveDownTheYAxis(1)
    private fun moveUpTheXAxis() = this.pos.moveUpTheXAxis(1)
}

data class Position(val x: Int, val y: Int) {
    fun moveUpTheYAxis(step: Int) = this.copy(y = this.y + step)
    fun moveDownTheYAxis(step: Int) = this.copy(y = this.y - step)
    fun moveUpTheXAxis(step: Int) = this.copy(x = this.x + step)
}

enum class MarsRoverCommand {
    Forwards
}

enum class FacingDirection {
    North,
    East,
    South,
    West,
}

fun land(landingPosition: Position, facingDirection: FacingDirection): Perseverance {
    return Perseverance(pos = landingPosition, facing = facingDirection)
}
