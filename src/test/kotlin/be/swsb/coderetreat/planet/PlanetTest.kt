package be.swsb.coderetreat.planet

import assertk.assertThat
import assertk.assertions.*
import be.swsb.coderetreat.rover.MovingDirection
import be.swsb.coderetreat.rover.Position
import org.junit.Test

class PlanetTest {

    @Test
    fun `wrapWhenCrossingEdge, given any position in the top edge when moving North, returns position in bottom edge`() {
        val `3x3 Planet` = Moon

        assertThat(`3x3 Planet`.wrapWhenCrossingEdge(Position(-1, 1), MovingDirection.NORTH)).isEqualTo(Position(-1, 1).flipY())
        assertThat(`3x3 Planet`.wrapWhenCrossingEdge(Position(0, 1), MovingDirection.NORTH)) .isEqualTo(Position(0, 1).flipY())
        assertThat(`3x3 Planet`.wrapWhenCrossingEdge(Position(1, 1), MovingDirection.NORTH)) .isEqualTo(Position(1, 1).flipY())
    }

    @Test
    fun `wrapWhenCrossingEdge, given any position in the bottom edge when moving South, returns position in top edge`() {
        val `3x3 Planet` = Moon

        assertThat(`3x3 Planet`.wrapWhenCrossingEdge(Position(-1, -1), MovingDirection.SOUTH)).isEqualTo(Position(-1, -1).flipY())
        assertThat(`3x3 Planet`.wrapWhenCrossingEdge(Position(0, -1),  MovingDirection.SOUTH)).isEqualTo(Position(0, -1).flipY())
        assertThat(`3x3 Planet`.wrapWhenCrossingEdge(Position(1, -1),  MovingDirection.SOUTH)).isEqualTo(Position(1, -1).flipY())
    }

    @Test
    fun `wrapWhenCrossingEdge, given any position in the right edge when moving East, returns position in left edge`() {
        val `3x3 Planet` = Moon

        assertThat(`3x3 Planet`.wrapWhenCrossingEdge(Position(1, 1), MovingDirection.EAST)).isEqualTo(Position(1, 1).flipX())
        assertThat(`3x3 Planet`.wrapWhenCrossingEdge(Position(1, 0), MovingDirection.EAST)) .isEqualTo(Position(1, 0).flipX())
        assertThat(`3x3 Planet`.wrapWhenCrossingEdge(Position(1, -1), MovingDirection.EAST)) .isEqualTo(Position(1, -1).flipX())
    }

    @Test
    fun `wrapWhenCrossingEdge, given any position in the left edge when moving West, returns position in right edge`() {
        val `3x3 Planet` = Moon

        assertThat(`3x3 Planet`.wrapWhenCrossingEdge(Position(-1, 1), MovingDirection.WEST)).isEqualTo(Position(-1, 1).flipX())
        assertThat(`3x3 Planet`.wrapWhenCrossingEdge(Position(-1, 0), MovingDirection.WEST)) .isEqualTo(Position(-1, 0).flipX())
        assertThat(`3x3 Planet`.wrapWhenCrossingEdge(Position(-1, -1), MovingDirection.WEST)) .isEqualTo(Position(-1, -1).flipX())
    }

    @Test
    fun `wrapWhenCrossingEdge, given any position outside the Planet's boundaries, throws exception`() {
        val `3x3 Planet` = Moon

        assertThat { `3x3 Planet`.wrapWhenCrossingEdge(Position(100,100), MovingDirection.EAST) }
                .thrownError { hasMessage("Out of bounds!"); isInstanceOf(OutOfBoundsException::class) }
    }

}

