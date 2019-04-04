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
        return if (inTopEdge(position) && movingDirection == Direction.NORTH) position.flipY()
        else if (inBottomEdge(position) && movingDirection == Direction.SOUTH) position.flipY()
        else if (inRightEdge(position) && movingDirection == Direction.EAST) position.flipX()
        else if (inLeftEdge(position) && movingDirection == Direction.WEST) position.flipX()
        else null
    }

    private fun inLeftEdge(position: Position) =
            dimension.width.div(2) == position.x.absoluteValue && position.x < 0

    private fun inRightEdge(position: Position) =
            dimension.width.div(2) == position.x.absoluteValue && position.x >= 0

    private fun inBottomEdge(position: Position) =
            dimension.height.div(2) == position.y.absoluteValue && position.y < 0

    private fun inTopEdge(position: Position) =
            dimension.height.div(2) == position.y.absoluteValue && position.y >= 0
}

typealias Mars = Planet.Mars
typealias Moon = Planet.Moon
