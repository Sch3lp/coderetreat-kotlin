package be.swsb.coderetreat.rover

data class Position(val x: Int, val y: Int) {
    fun stepY(direction: StepDirection): Position {
        return when (direction) {
            StepDirection.UP -> Position(x, y + 1)
            StepDirection.DOWN -> Position(x, y - 1)
        }
    }

    fun stepX(direction: StepDirection): Position {
        return when (direction) {
            StepDirection.UP -> Position(x + 1, y)
            StepDirection.DOWN -> Position(x - 1, y)
        }
    }

    fun flipY(): Position {
        return Position(this.x, this.y * -1)
    }

    fun flipX(): Position {
        return Position(this.x * -1, this.y)
    }
}

enum class StepDirection {
    UP,
    DOWN;

    fun flip(): StepDirection {
        return when(this) {
            UP -> DOWN
            DOWN -> UP
        }
    }
}