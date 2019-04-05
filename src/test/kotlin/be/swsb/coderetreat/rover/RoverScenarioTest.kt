package be.swsb.coderetreat.rover

import assertk.assertThat
import assertk.assertions.isEqualTo
import be.swsb.coderetreat.planet.Planet
import org.junit.Test

class RoverScenarioTest {

    @Test
    fun `a default Rover receives a bunch of commands, and we check its end-state`() {
        val defaultRover = Rover()

        val actual = defaultRover.receiveCommands(listOf(Forwards, Left, Forwards, Right, Forwards, Left, Backwards, Backwards))

        assertThat(actual).isEqualTo(Rover(facingDirection = Direction.WEST, position = Position(1, 2)))
    }

    @Test
    fun `a default Rover receives a bunch of commands, of which the middle one would cause the rover to crash into an obstacle`() {
        val obstaclePosition = Position(0, -2)

        val mars = Planet.Mars(listOf(obstaclePosition))

        val defaultRover = Rover(planet = mars)

        val actual = defaultRover.receiveCommands(listOf(Forwards, Forwards, Forwards, Forwards, Forwards))

        assertThat(actual).isEqualTo(Rover(facingDirection = Direction.NORTH, position = Position(0, 2), planet = mars))
        assertThat(actual.message).isEqualTo("There is an obstacle at (0,-2), ignoring further commands.")
    }
}