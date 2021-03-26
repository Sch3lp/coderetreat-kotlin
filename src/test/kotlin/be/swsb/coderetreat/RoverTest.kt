package be.swsb.coderetreat

import be.swsb.coderetreat.Position.Companion.at
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class RoverTest {

    @Test
    fun `a rover can receive a Position and a Direction to start reporting on it`() {
        val actual = Rover()
        assertThat(actual).isEqualTo(Rover(position = at(0,0), direction = Direction.North))
    }

}


data class Rover(val position : Position, val direction: Direction)

data class Position(private val x: Int, private val y: Int) {
    companion object {
        fun at(x: Int, y: Int): Position = Position(x,y)
    }
}
sealed class Direction {
    object North: Direction()
    object East: Direction()
    object South: Direction()
    object West: Direction()
}