package be.swsb.coderetreat

import be.swsb.coderetreat.Direction.*

data class PlayerField(private val grid: Map<Point, String> = emptyMap()) {
    fun place(ship: Ship, startingPoint: Point, direction: Direction): PlayerField {
        val shipCoordinates = when (direction) {
            Horizontally -> startingPoint..<(startingPoint + Point(ship.length, 0))
            Vertically -> startingPoint..<(startingPoint + Point(0, ship.length))
        }
        validatePointsAreInBounds(shipCoordinates, ship, direction)
        val newGrid = grid + shipCoordinates.map { it to ship.representation }.toMap()
        return copy(grid = newGrid)
    }

    private fun validatePointsAreInBounds(shipCoordinates: List<Point>, ship: Ship, direction: Direction) {
        val boundedPoints = (1..10).flatMap { x ->
            (1..10).map { y -> Point(x, y) }
        }
        val shipCoordinatesOutOfBounds = shipCoordinates.any { point -> point !in boundedPoints }
        if (shipCoordinatesOutOfBounds) throw PlacementOutOfBounds(ship, direction, shipCoordinates.first())
    }

    fun render(): String =
        (1..10).joinToString("\n") { y ->
            (1..10).joinToString("") { x ->
                render(Point(x, y))
            }
        }

    private fun render(renderPoint: Point): String =
        grid[renderPoint] ?: """🌊"""
}

sealed class Ship(val representation: String, val length: Int)
data object Carrier : Ship("""⛴️""", 5)
enum class Direction {
    Horizontally,
    Vertically,
}

class PlacementOutOfBounds(ship: Ship, direction: Direction, startingPoint: Point): Exception("Placing a $ship $direction at $startingPoint is out of bounds")