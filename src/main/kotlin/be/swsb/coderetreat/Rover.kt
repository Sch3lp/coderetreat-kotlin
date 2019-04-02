package be.swsb.coderetreat

data class Rover(val facingDirection: Direction = Direction.NORTH,
                 val position: Position = Position(0, 0)) {

    fun receiveCommand(command: RoverCommand): Rover {
        return when (command) {
            is RoverCommand.MoveCommand -> Rover(facingDirection = this.facingDirection, position = move(command))
            is RoverCommand.RotateCommand -> Rover(facingDirection = rotate(command), position = this.position)
        }
    }

    private fun rotate(rotateCommand: RoverCommand.RotateCommand): Direction {
        return when (rotateCommand) {
            is Right -> facingDirection.rotateClockwise()
            is Left  -> facingDirection.rotateCounterClockwise()
        }
    }

    private fun move(moveCommand: RoverCommand.MoveCommand): Position {
        val stepDirection = when (moveCommand) {
            is Forwards  -> StepDirection.UP
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
