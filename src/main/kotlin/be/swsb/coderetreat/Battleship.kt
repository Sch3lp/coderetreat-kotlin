package be.swsb.coderetreat


class BattleshipField(
    val placedShip: PlacedShip? = null,
) {

    private val PlacedShip.icon: String get() = CellIcon.Carrier.icon

    fun render(): String {
        return (0..<10).joinToString("\n") { y ->
            (0..<10).joinToString("") { x ->
                if (placedShip != null && Pair(x,y) in placedShip.positions) {
                    placedShip.icon
                } else {
                    CellIcon.Wave.icon
                }
            }
        }
    }

    fun placeShip(ship: Ship, x: Int, y: Int, direction: Direction): BattleshipField {
        val placedShip = PlacedShip(
            ship = ship,
            x = x,
            y = y,
            direction = direction,
        )
        return BattleshipField(placedShip = placedShip)
    }
}

data class PlacedShip(
    val x: Int = 0,
    val y: Int = 0,
    val ship: Ship,
    val direction: Direction,
) {
    val positions: List<Pair<Int, Int>> =
        (0..<ship.length)
            .map { i ->
                if (direction == Direction.Horizontal) {
                    Pair(x + i, y)
                } else {
                    Pair(x, y + i)
                }
            }
}

enum class CellIcon(val icon: String) {
    Wave("""🌊"""),
    Carrier("""🛳️"""),
}

enum class Ship(val length: Int) {
    Carrier(5),
    Battleship(4),
    Destroyer(3),
    Submarine(3),
    PatrolBoat(2),
}

enum class Direction {
    Horizontal, Vertical
}