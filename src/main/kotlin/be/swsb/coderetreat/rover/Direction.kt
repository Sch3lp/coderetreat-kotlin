package be.swsb.coderetreat.rover

enum class Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    fun rotateClockwise(): Direction {
        return when (this) {
            NORTH -> EAST
            EAST -> SOUTH
            SOUTH -> WEST
            WEST -> NORTH
        }
    }

    fun rotateCounterClockwise(): Direction {
        return when (this) {
            NORTH -> WEST
            WEST -> SOUTH
            SOUTH -> EAST
            EAST -> NORTH
        }
    }

    fun flip(): Direction {
        return when(this) {
            NORTH -> SOUTH
            SOUTH -> NORTH
            EAST -> WEST
            WEST -> EAST
        }
    }
}

typealias MovingDirection = Direction