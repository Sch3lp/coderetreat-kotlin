package be.swsb.coderetreat.planet

import assertk.assertThat
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
    fun `isAnEdge Given a Planet Of 3x3, anything but (0,0) is an Edge`() {
        val `3x3 Planet` = Planet(`3x3`())

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
}

