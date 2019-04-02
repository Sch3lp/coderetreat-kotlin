package be.swsb.coderetreat

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test

class RoverScenarioTest {

    @Test
    fun aDefaultRover_Forwards_Left_Forwards_Right_Forwards_Left_Backwards_Backwards() {
        val defaultRover = Rover()

        val actual = defaultRover.receiveCommand(Forwards)
                .receiveCommand(Left)
                .receiveCommand(Forwards)
                .receiveCommand(Right)
                .receiveCommand(Forwards)
                .receiveCommand(Left)
                .receiveCommand(Backwards)
                .receiveCommand(Backwards)

        assertThat(actual).isEqualTo(Rover(facingDirection = Direction.WEST, position = Position(1,2)))
    }
}