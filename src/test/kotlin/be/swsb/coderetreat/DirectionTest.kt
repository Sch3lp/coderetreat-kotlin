package be.swsb.coderetreat

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test

class DirectionTest {

    @Test
    fun rotateClockwise() {
        assertThat(Direction.NORTH.rotateClockwise()).isEqualTo(Direction.EAST)
        assertThat(Direction.EAST.rotateClockwise()).isEqualTo(Direction.SOUTH)
        assertThat(Direction.SOUTH.rotateClockwise()).isEqualTo(Direction.WEST)
        assertThat(Direction.WEST.rotateClockwise()).isEqualTo(Direction.NORTH)
    }

    @Test
    fun rotateCounterClockwise() {
        assertThat(Direction.NORTH.rotateCounterClockwise()).isEqualTo(Direction.WEST)
        assertThat(Direction.WEST.rotateCounterClockwise()).isEqualTo(Direction.SOUTH)
        assertThat(Direction.SOUTH.rotateCounterClockwise()).isEqualTo(Direction.EAST)
        assertThat(Direction.EAST.rotateCounterClockwise()).isEqualTo(Direction.NORTH)
    }
}