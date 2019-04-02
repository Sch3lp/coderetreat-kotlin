package be.swsb.coderetreat

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test

class RoverTest {

    @Test
    fun aDefaultRoverShouldBeFacingNorth() {
        val defaultRover = Rover()

        assertThat(defaultRover.facingDirection).isEqualTo(Direction.NORTH)
    }

    @Test
    fun aDefaultRoverShouldBePositionedAt00() {
        val defaultRover = Rover()

        assertThat(defaultRover.position).isEqualTo(Position(0,0))
    }
}