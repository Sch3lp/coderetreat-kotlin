package be.swsb.coderetreat.rover

import assertk.assertThat
import assertk.assertions.isEqualTo
import be.swsb.coderetreat.rover.Position
import be.swsb.coderetreat.rover.StepDirection
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
    fun flipY_ReturnsNewPositionWithNegatedY() {
        val positiveY = Position(71263931,567).flipY()
        assertThat(positiveY).isEqualTo(Position(71263931, -567))

        val negativeY = Position(76234,-567).flipY()
        assertThat(negativeY).isEqualTo(Position(76234, 567))
    }

    @Test
    fun flipX_ReturnsNewPositionWithNegatedX() {
        val positiveX = Position(71263931,1287531).flipX()
        assertThat(positiveX).isEqualTo(Position(-71263931, 1287531))

        val negativeX = Position(-348756,-7513).flipX()
        assertThat(negativeX).isEqualTo(Position(348756, -7513))
    }

    @Test
    fun asString() {
        assertThat(Position(1,0).asString()).isEqualTo("(1,0)")
        assertThat(Position(-9876,98349874).asString()).isEqualTo("(-9876,98349874)")
    }
}