package be.swsb.coderetreat

import be.swsb.coderetreat.Position.Companion.at
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class RoverTest {

/*
Rules of Simple Design
1. Passes tests
2. Reveals intent
3. No duplication of concepts
4. Fewest elements possible
*/

    @Test
    fun `a rover can receive a Position and a Direction to start reporting on it`() {
        val actual = Rover()
        assertThat(actual).isEqualTo(Rover(position = at(0,0), direction = Direction.North))
    }

    @Test
    internal fun `when a rover recieves a forwards command then moves up the y axis`() {
        val actual = Rover().receive(Command.Forwards)
        assertThat(actual).isEqualTo(Rover(position = at(0,1), direction = Direction.North))

    }
}


data class Rover(val position : Position = at(0,0), val direction: Direction = Direction.North) {
    fun receive(command: Command): Rover {
        return this.copy(position = at(0, 1))
    }
}

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

sealed class Command {
    object Forwards: Command()
}