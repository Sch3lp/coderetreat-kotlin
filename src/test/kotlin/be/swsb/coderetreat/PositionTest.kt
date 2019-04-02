package be.swsb.coderetreat

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test

class PositionTest {
    @Test
    fun stepY_DirectionUp_ShouldIncreaseYWith1() {
        val initialPosition = Position(9719817, 98765)

        val actual = initialPosition.stepY(StepDirection.UP)

        assertThat(actual).isEqualTo(Position(9719817, 98766))
    }

    @Test
    fun stepY_DirectionDown_ShouldDecreaseYWith1() {
        val initialPosition = Position(239289, 4567)

        val actual = initialPosition.stepY(StepDirection.DOWN)

        assertThat(actual).isEqualTo(Position(239289, 4566))
    }

    @Test
    fun stepX_DirectionUp_ShouldIncreaseXWith1() {
        val initialPosition = Position(873782, 249875)

        val actual = initialPosition.stepX(StepDirection.UP)

        assertThat(actual).isEqualTo(Position(873783, 249875))
    }

    @Test
    fun stepX_DirectionDown_ShouldDecreaseXWith1() {
        val initialPosition = Position(185685, 948576)

        val actual = initialPosition.stepX(StepDirection.DOWN)

        assertThat(actual).isEqualTo(Position(185684, 948576))
    }

    @Test
    fun flipStepDirection_UpBecomesDownAndViceVersa() {
        assertThat(StepDirection.UP.flip()).isEqualTo(StepDirection.DOWN)
        assertThat(StepDirection.DOWN.flip()).isEqualTo(StepDirection.UP)
    }
}