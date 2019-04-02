package be.swsb.coderetreat

data class Rover(val facingDirection: Direction = Direction.NORTH,
                 val position: Position = Position(0, 0)) {

    fun receiveCommand(command: Command): Rover {
        return Rover(facingDirection = this.facingDirection, position = moveForwards())
    }

    private fun moveForwards(): Position {
        return when (facingDirection) {
            Direction.NORTH -> Position(0, position.y + 1)
            Direction.EAST -> Position(position.x + 1, 0)
            Direction.SOUTH -> Position(0, position.y - 1)
            Direction.WEST -> Position(position.x -1, 0)
        }
    }

}

enum class Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST
}

enum class Command {
    FORWARDS
}