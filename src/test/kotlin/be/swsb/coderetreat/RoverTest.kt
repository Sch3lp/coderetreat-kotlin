package be.swsb.coderetreat

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


class RoverTest {

    @Test
    fun `A Rover can be initiated with a starting position and a starting facing direction`() {
        val initiatedRover = defaultRover()
        assertThat(initiatedRover).isEqualTo(Rover(at = Position(0, 0), facing = Direction.North))
    }

    @Nested
    inner class ReceivingForwards {
        @Test
        fun `A Rover facing North, moves up the Y axis`() {
            val initiatedRover = defaultRover().receive(Command.Forwards)
            assertThat(initiatedRover).isEqualTo(Rover(at = Position(0, 1), facing = Direction.North))
        }

        @Test
        fun `A Rover facing North, receiving forward twice, moves up the Y axis twice`() {
            val initiatedRover = defaultRover().receive(Command.Forwards).receive(Command.Forwards)
            assertThat(initiatedRover).isEqualTo(Rover(at = Position(0, 2), facing = Direction.North))
        }
    }
}

data class Rover(private val at: Position = Position(0, 0), private val facing: Direction = Direction.North) {
    fun receive(command: Command): Rover {
        return this.copy(at = at.moveUp())
    }
}

enum class Direction {
    North
}

enum class Command {
    Forwards
}

data class Position(private val x: Int, private val y: Int) {
    fun moveUp(): Position {
        return this.copy(y = this.y + 1)
    }
}

fun defaultRover() = Rover()
