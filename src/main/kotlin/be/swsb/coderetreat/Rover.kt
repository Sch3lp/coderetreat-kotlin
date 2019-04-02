package be.swsb.coderetreat

data class Rover(val facingDirection: Direction = Direction.NORTH,
                 val position: Position = Position(0, 0)) {

    fun receiveCommand(command: RoverCommand): Rover {
        return when (command) {
            is RoverCommand.MoveCommand -> Rover(facingDirection = this.facingDirection, position = move(command))
            is RoverCommand.RotateCommand -> Rover(facingDirection = this.facingDirection, position = this.position)
        }
    }

    private fun move(moveCommand: RoverCommand.MoveCommand): Position {
        val stepDirection = when (moveCommand) {
            is Forwards -> StepDirection.UP
            is Backwards -> StepDirection.DOWN
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
    WEST;
}


sealed class RoverCommand {
    sealed class MoveCommand : RoverCommand()  {
        object Forwards : MoveCommand()
        object Backwards : MoveCommand()
    }
    sealed class RotateCommand : RoverCommand()  {
        object Right : RotateCommand()
        object Left : RotateCommand()
    }
}

typealias Forwards = RoverCommand.MoveCommand.Forwards
typealias Backwards = RoverCommand.MoveCommand.Backwards
typealias Right = RoverCommand.RotateCommand.Right
typealias Left = RoverCommand.RotateCommand.Left
