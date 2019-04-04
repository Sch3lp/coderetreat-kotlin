package be.swsb.coderetreat.planet

import be.swsb.coderetreat.rover.Position
import kotlin.math.absoluteValue

sealed class Planet(private val dimension: Dimension) {
    object Mars: Planet(`5x5`())
    object Moon: Planet(`3x3`())

    fun isAnEdge(position: Position): Boolean {
        return dimension.height.div(2) == position.y.absoluteValue
            || dimension.width.div(2) == position.x.absoluteValue
    }

    fun edge(position: Position): Edge {
        return if (dimension.height.div(2) == position.y.absoluteValue && position.y >= 0) TopEdge
        else if (dimension.height.div(2) == position.y.absoluteValue && position.y < 0) BottomEdge
        else if (dimension.width.div(2) == position.x.absoluteValue && position.x >= 0) RightEdge
        else LeftEdge
    }
}

typealias Mars = Planet.Mars
typealias Moon = Planet.Moon

sealed class Edge {
    object TopEdge: Edge()
    object BottomEdge: Edge()
    object LeftEdge: Edge()
    object RightEdge: Edge()
}

typealias TopEdge = Edge.TopEdge
typealias BottomEdge = Edge.BottomEdge
typealias LeftEdge = Edge.LeftEdge
typealias RightEdge = Edge.RightEdge