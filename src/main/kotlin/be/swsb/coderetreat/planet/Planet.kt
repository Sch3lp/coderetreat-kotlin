package be.swsb.coderetreat.planet

import be.swsb.coderetreat.rover.Direction
import be.swsb.coderetreat.rover.MovingDirection
import be.swsb.coderetreat.rover.Position
import kotlin.math.absoluteValue

sealed class Planet(private val dimension: Dimension) {
    object Mars : Planet(`5x5`())
    object Moon : Planet(`3x3`())

    fun isAnEdge(position: Position): Boolean {
        return dimension.height.div(2) == position.y.absoluteValue
                || dimension.width.div(2) == position.x.absoluteValue
    }

    fun wrapWhenCrossingEdge(position: Position, movingDirection: MovingDirection): Position? {
        throwExceptionWhenOutOfBounds(position)
        return if (onTopEdge(position) && movingDirection == Direction.NORTH) position.flipY()
        else if (onBottomEdge(position) && movingDirection == Direction.SOUTH) position.flipY()
        else if (onRightEdge(position) && movingDirection == Direction.EAST) position.flipX()
        else if (onLeftEdge(position) && movingDirection == Direction.WEST) position.flipX()
        else null
    }

    private fun throwExceptionWhenOutOfBounds(position: Position) {
        if (outsideTopEdge(position) || outsideBottomEdge(position) || outsideLeftEdge(position) || outsideRightEdge(position))
            throw OutOfBoundsException()
    }

    private fun outsideTopEdge(position: Position): Boolean {
        return position.y > topEdge()
    }

    private fun outsideBottomEdge(position: Position): Boolean {
        return position.y < bottomEdge()
    }

    private fun outsideRightEdge(position: Position): Boolean {
        return position.x > rightEdge()
    }

    private fun outsideLeftEdge(position: Position): Boolean {
        return position.x < leftEdge()
    }

    private fun onTopEdge(position: Position) =
            topEdge() == position.y

    private fun onBottomEdge(position: Position) =
            bottomEdge() == position.y

    private fun onRightEdge(position: Position) =
            rightEdge() == position.x

    private fun onLeftEdge(position: Position) =
            leftEdge() == position.x

    private fun topEdge() = dimension.height.div(2)
    private fun bottomEdge() = (dimension.height.div(2) * -1)
    private fun rightEdge() = dimension.width.div(2)
    private fun leftEdge() = (dimension.width.div(2) * -1)
}

typealias Mars = Planet.Mars
typealias Moon = Planet.Moon

class OutOfBoundsException: Exception("Out of bounds!")