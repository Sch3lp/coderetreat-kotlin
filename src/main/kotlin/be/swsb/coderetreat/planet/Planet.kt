package be.swsb.coderetreat.planet

import be.swsb.coderetreat.rover.Position
import kotlin.math.absoluteValue

sealed class Planet(val dimension: Dimension) {
    object Mars: Planet(`5x5`())
    object Moon: Planet(`3x3`())

    fun isAnEdge(position: Position): Boolean {
        return dimension.height.div(2).absoluteValue == position.y.absoluteValue
            || dimension.width.div(2).absoluteValue == position.x.absoluteValue
    }
}

typealias Mars = Planet.Mars
typealias Moon = Planet.Moon