package be.swsb.coderetreat


class BattleshipField(
    val placedShip: PlacedShip? = null,
) {

    private val PlacedShip.icon: String get() = CellIcon.Carrier.icon

    fun render(): String {
        return (0..<10).joinToString("\n") { y ->
            (0..<10).joinToString("") { x ->
                if (placedShip != null && placedShip.x == x && placedShip.y == y) {
                    placedShip.icon
                } else {
                    CellIcon.Wave.icon
                }
            }
        }
    }

    fun placeShip(ship: Ship, x: Int, y: Int, direction: Direction): BattleshipField {
        return BattleshipField(placedShip = PlacedShip(
            ship = ship,
            x = x,
            y = y,
            direction = direction,
        ))
    }
}

data class PlacedShip(
    val x: Int = 0,
    val y: Int = 0,
    val ship: Ship,
    val direction: Direction,
)

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