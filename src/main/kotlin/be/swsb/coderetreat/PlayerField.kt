package be.swsb.coderetreat

import be.swsb.coderetreat.Direction.*

data class PlacedShip(
    val ship: Ship,
    val coordinates: Set<Point>,
    private val damage: Set<Point> = emptySet(),
) {
    private val hasSunken get() = coordinates == damage

    fun damage(point: Point): PlacedShip =
        copy(damage = damage + point)

    fun render(point: Point) = when {
        hasSunken -> """🏊"""
        point in damage -> """💥"""
        else -> ship.representation
    }
}

data class PlayerField(
    private val ships: List<PlacedShip> = emptyList()
) {
    private val grid: Map<Point, String> = ships.flatMap { ship ->
        ship.coordinates.map { point -> point to ship.render(point) }
    }.toMap()

    fun place(ship: Ship, startingPoint: Point, direction: Direction): PlayerField {
        val shipCoordinates = when (direction) {
            Horizontally -> startingPoint..<(startingPoint + Point(ship.length, 0))
            Vertically -> startingPoint..<(startingPoint + Point(0, ship.length))
        }
        validatePointsAreInBounds(shipCoordinates, ship, direction)
        val newShips = ships + PlacedShip(ship, shipCoordinates.toSet())
        return copy(ships = newShips)
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

    fun fire(target: Point): PlayerField {
        val newShips = shipAt(target)?.damage(target)
            ?.let { ships.remove(it.ship) + it }
            ?: ships
        return copy(ships = newShips)
    }

    private fun List<PlacedShip>.remove(ship: Ship): List<PlacedShip> = toMutableList().apply {
        val shipToRemove = indexOfFirst { it.ship == ship }
        removeAt(shipToRemove)
    }

    private fun shipAt(target: Point): PlacedShip? {
        return ships.firstOrNull { ship -> target in ship.coordinates }
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

class PlacementOutOfBounds(ship: Ship, direction: Direction, startingPoint: Point) :
    Exception("Placing a $ship $direction at $startingPoint is out of bounds")