package be.swsb.coderetreat

import be.swsb.coderetreat.MarsRoverCommand.Forwards
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class PerseveranceTest {

    @Test
    fun `Perseverance lands on a position and a direction that it's facing towards`() {
        val landedPerseverance = land(Position(0, 0), "North")

        assertThat(landedPerseverance)
            .isEqualTo(Perseverance(pos = Position(0, 0), facing = "North"))
    }

    @Test
    fun `Perseverance can receive a forwards command and move towards a new position`() {
        val landedPerseverance = land(Position(0, 0), "North")

        val updatedRover = landedPerseverance.receive(Forwards)

        assertThat(updatedRover)
            .isEqualTo(Perseverance(pos = Position(x = 0, y = 1), facing = "North"))
    }

    @Test
    fun `Perseverance can receive a forwards command twice and move towards a new position`() {
        val landedPerseverance = land(Position(0, 0), "North")

        val updatedRover: Perseverance = landedPerseverance
            .receive(Forwards)
            .receive(Forwards)

        assertThat(updatedRover)
            .isEqualTo(Perseverance(pos = Position(x = 0, y = 2), facing = "North"))
    }

}

data class Perseverance(
    val pos: Position,
    val facing: String,
) {
    fun receive(command: MarsRoverCommand): Perseverance {
        return copy(pos = moveUpTheYAxis())
    }

    private fun moveUpTheYAxis() = this.pos.moveUpTheYAxis(1)
}

data class Position(val x: Int, val y: Int) {
    fun moveUpTheYAxis(step: Int): Position {
        return this.copy(y = this.y + step)
    }
}

enum class MarsRoverCommand {
    Forwards
}

fun land(landingPosition: Position, facingDirection: String): Perseverance {
    return Perseverance(pos = landingPosition, facing = facingDirection)
}
