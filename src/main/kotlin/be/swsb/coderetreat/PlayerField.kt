package be.swsb.coderetreat

import be.swsb.coderetreat.Direction.*
import be.swsb.coderetreat.FireResult.*

data class PlacedShip(
    val ship: Ship,
    val coordinates: Set<Point>,
    private val damage: Set<Point> = emptySet(),
) {
    val hasSunken get() = coordinates == damage

    fun damage(point: Point): PlacedShip =
        copy(damage = damage + point)

    fun render(point: Point) = when {
        hasSunken -> """🏊"""
        point in damage -> """💥"""
        else -> ship.representation
    }
}

data class PlayerField(
    private val ships: List<PlacedShip> = emptyList(),
    val lastFireResult: FireResult = NothingHappened,
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
        validateShipWasNotPlacedYet(ship)
        validateShipDoesNotOverlapWithAnAlreadyPlacedShip(shipCoordinates, ship)
        val newShips = ships + PlacedShip(ship, shipCoordinates.toSet())
        return copy(ships = newShips)
    }

    private fun validateShipDoesNotOverlapWithAnAlreadyPlacedShip(shipCoordinates: List<Point>, ship: Ship) {
        val overlappingShips = shipCoordinates.mapNotNull { shipAt(it) }
        if (overlappingShips.isNotEmpty()) throw PlacementOverlaps(ship, overlappingShips.first().ship)
    }

    private fun validatePointsAreInBounds(shipCoordinates: List<Point>, ship: Ship, direction: Direction) {
        val boundedPoints = (1..10).flatMap { x ->
            (1..10).map { y -> Point(x, y) }
        }
        val shipCoordinatesOutOfBounds = shipCoordinates.any { point -> point !in boundedPoints }
        if (shipCoordinatesOutOfBounds) throw PlacementOutOfBounds(ship, direction, shipCoordinates.first())
    }

    private fun validateShipWasNotPlacedYet(ship: Ship) {
        if (ship in ships.map { it.ship }) throw Cheater("Where did you get that extra Carrier from? Cheater!")
    }

    fun render(): String =
        (1..10).joinToString("\n") { y ->
            (1..10).joinToString("") { x ->
                render(Point(x, y))
            }
        }

    fun fire(target: Point): PlayerField {
        val fireResult = if (shipAt(target) != null) Hit else Miss
        val newShips = shipAt(target)?.damage(target)
            ?.let { damagedShip -> ships.remove(damagedShip.ship) + damagedShip }
            ?: ships
        return copy(ships = newShips, lastFireResult = fireResult)
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

    fun isComplete(): Boolean =
        ships.map { it.ship::class }
            .containsAll(Ship::class.sealedSubclasses)

    fun allShipsSunk(): Boolean =
        ships.all { it.hasSunken }
}


sealed class Ship(val representation: String, val length: Int)
data object Carrier : Ship("""⛴️""", 5)
data object Battleship : Ship("""🛳️""", 4)
data object Destroyer : Ship("""🛥️""", 3)
data object Submarine : Ship("""🚤""", 3)
data object PatrolBoat : Ship("""🛶""", 2)

enum class Direction {
    Horizontally,
    Vertically,
}

class PlacementOutOfBounds(ship: Ship, direction: Direction, startingPoint: Point) :
    Exception("Placing a $ship $direction at $startingPoint is out of bounds")

class PlacementOverlaps(violatingShip: Ship, placedShip: Ship) :
    Exception("Placing this $violatingShip would overlap with the already placed $placedShip")

class Cheater(message: String) :
    Exception(message)