package be.swsb.coderetreat

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class PerseveranceTest {

    @Test
    fun `Perseverance lands on a position and a direction that it's facing towards`() {
        val landedPerseverance = land(Position(0, 0), "North")

        assertThat(landedPerseverance)
            .isEqualTo(MarsRover(name = "Perseverance", pos = Position(0,0), facing = "North"))
    }
}

fun land(landingPosition: Position, facingDirection: String): MarsRover {
    return MarsRover("Perseverance", pos = landingPosition, facing = facingDirection)
}

data class Position(val x: Int, val y: Int)
data class MarsRover(
    val name: String = "Perseverance",
    val pos: Position,
    val facing: String,
)