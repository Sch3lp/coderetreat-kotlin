package be.swsb.coderetreat

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class RoverTest {

    @Test
    fun `A Rover can be initiated with a starting position and a starting facing direction`() {
        val initiatedRover = defaultRover()
        assertThat(initiatedRover).isEqualTo(Rover(at = Pair(0,0), facing = Direction.North))
    }


}


data class Rover(private val at: Position = Pair(0,0), private val facing: Direction = Direction.North)

enum class Direction {
    North
}

typealias Position = Pair<Int,Int>

fun defaultRover() = Rover()
