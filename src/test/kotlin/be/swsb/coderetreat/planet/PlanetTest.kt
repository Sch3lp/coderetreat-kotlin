package be.swsb.coderetreat.planet

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import be.swsb.coderetreat.rover.Position
import org.junit.Test

class PlanetTest {

    // The Moon
    // (-1, 1) (0, 1) (1,  1)
    // (-1, 0) (0, 0) (1,  0)
    // (-1,-1) (0,-1) (1, -1)
    @Test
    fun `isAnEdge Given a Planet Of 3x3, anything but (0,0) is an edge`() {
        val `3x3 Planet` = Moon

        assertThat(`3x3 Planet`.isAnEdge(Position(0, 0))).isFalse()

        //top edge
        assertThat(`3x3 Planet`.isAnEdge(Position(-1, 1))).isTrue()
        assertThat(`3x3 Planet`.isAnEdge(Position(0, 1))).isTrue()
        assertThat(`3x3 Planet`.isAnEdge(Position(1, 1))).isTrue()

        //bottom edge
        assertThat(`3x3 Planet`.isAnEdge(Position(-1, -1))).isTrue()
        assertThat(`3x3 Planet`.isAnEdge(Position(0, -1))).isTrue()
        assertThat(`3x3 Planet`.isAnEdge(Position(1, -1))).isTrue()

        //middle left edge
        assertThat(`3x3 Planet`.isAnEdge(Position(-1, 0))).isTrue()

        //middle right edge
        assertThat(`3x3 Planet`.isAnEdge(Position(1, 0))).isTrue()
    }

    @Test
    fun `edge, given any position in the top edge, returns the top edge`() {
        val `3x3 Planet` = Moon

        assertThat(`3x3 Planet`.edge(Position(-1, 1))).isEqualTo(TopEdge)
        assertThat(`3x3 Planet`.edge(Position(0, 1))) .isEqualTo(TopEdge)
        assertThat(`3x3 Planet`.edge(Position(1, 1))) .isEqualTo(TopEdge)
    }

    @Test
    fun `edge, given any position in the bottom edge, returns the bottom edge`() {
        val `3x3 Planet` = Moon

        assertThat(`3x3 Planet`.edge(Position(-1, -1))).isEqualTo(BottomEdge)
        assertThat(`3x3 Planet`.edge(Position(0, -1))) .isEqualTo(BottomEdge)
        assertThat(`3x3 Planet`.edge(Position(1, -1))) .isEqualTo(BottomEdge)
    }

    @Test
    fun `edge, given any position in the right edge, returns the right edge`() {
        val `3x3 Planet` = Moon

        assertThat(`3x3 Planet`.edge(Position(1, 1))).isEqualTo(RightEdge)
        assertThat(`3x3 Planet`.edge(Position(1, 0))) .isEqualTo(RightEdge)
        assertThat(`3x3 Planet`.edge(Position(1, -1))) .isEqualTo(RightEdge)
    }

    @Test
    fun `edge, given any position in the left edge, returns the left edge`() {
        val `3x3 Planet` = Moon

        assertThat(`3x3 Planet`.edge(Position(-1, 1))).isEqualTo(LeftEdge)
        assertThat(`3x3 Planet`.edge(Position(-1, 0))) .isEqualTo(LeftEdge)
        assertThat(`3x3 Planet`.edge(Position(-1, -1))) .isEqualTo(LeftEdge)
    }
}

