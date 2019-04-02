package be.swsb.coderetreat

data class Rover(val facingDirection: Direction = Direction.NORTH,
                 val position: Position = Position(0, 0)) {

    fun receiveCommand(command: Command): Rover {
        return if (command == Command.FORWARDS) {
            Rover(facingDirection = this.facingDirection, position = move(command))
        } else {
            Rover(facingDirection = this.facingDirection, position = move(command))
        }
    }

    private fun move(moveCommand: Command): Position {
        val stepDirection = when (moveCommand) {
            Command.FORWARDS  -> StepDirection.UP
            Command.BACKWARDS -> StepDirection.DOWN
        }

        return when (facingDirection) {
            Direction.NORTH -> position.stepY(stepDirection)
            Direction.EAST -> position.stepX(stepDirection)
            Direction.SOUTH -> position.stepY(stepDirection.flip())
            Direction.WEST -> position.stepX(stepDirection.flip())
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
    FORWARDS,
    BACKWARDS
}