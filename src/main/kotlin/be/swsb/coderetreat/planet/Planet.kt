package be.swsb.coderetreat.planet

import be.swsb.coderetreat.rover.Position
import kotlin.math.absoluteValue

data class Planet(val dimension: Dimension) {
    companion object {
        fun mars(): Planet {
            return Planet(`4x4`())
        }
    }

    fun isAnEdge(position: Position): Boolean {
        return dimension.height.div(2).absoluteValue == position.y.absoluteValue
            || dimension.width.div(2).absoluteValue == position.x.absoluteValue
    }
}