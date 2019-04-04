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
        return if (dimension.height.div(2) == position.y.absoluteValue && position.y >= 0 && movingDirection == Direction.NORTH) position.flipY()
        else if (dimension.height.div(2) == position.y.absoluteValue && position.y < 0 && movingDirection == Direction.SOUTH) position.flipY()
        else if (dimension.width.div(2) == position.x.absoluteValue && position.x >= 0 && movingDirection == Direction.EAST) position.flipX()
        else if (dimension.width.div(2) == position.x.absoluteValue && position.x < 0 && movingDirection == Direction.WEST) position.flipX()
        else null
    }
}

typealias Mars = Planet.Mars
typealias Moon = Planet.Moon
