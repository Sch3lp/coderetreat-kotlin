package be.swsb.coderetreat.rover

import assertk.assertThat
import assertk.assertions.isEqualTo
import be.swsb.coderetreat.rover.*
import org.junit.Test

class RoverScenarioTest {

    @Test
    fun aDefaultRover_Forwards_Left_Forwards_Right_Forwards_Left_Backwards_Backwards() {
        val defaultRover = Rover()

        val actual = defaultRover.receiveCommands(listOf(Forwards, Left, Forwards, Right, Forwards, Left, Backwards, Backwards))

        assertThat(actual).isEqualTo(Rover(facingDirection = Direction.WEST, position = Position(1, 2)))
    }
}