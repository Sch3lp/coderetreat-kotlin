package be.swsb.coderetreat.planet

import org.junit.Test

class PlanetTest {

    @Test
    fun canBeCreatedWithAGivenDimension() {
        Planet(`4 x 4`())
    }
}

