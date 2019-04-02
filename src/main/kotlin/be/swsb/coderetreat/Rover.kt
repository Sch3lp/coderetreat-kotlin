package be.swsb.coderetreat

data class Rover(val facingDirection: Direction = Direction.NORTH,
                 val position: Position = Position(0, 0)) {

    fun receiveCommand(command: Command): Rover {
        return when (command) {
            is Command.MoveCommand -> Rover(facingDirection = this.facingDirection, position = move(command))
            is Command.RotateCommand -> Rover(facingDirection = rotate(command), position = this.position)
        }
    }

    private fun rotate(rotateCommand: Command.RotateCommand): Direction {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun move(moveCommand: Command.MoveCommand): Position {
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
    WEST
}


sealed class Command {
    sealed class MoveCommand : Command()  {
        object Forwards : MoveCommand()
        object Backwards : MoveCommand()
    }
    sealed class RotateCommand : Command()  {
        object Right : RotateCommand()
        object Left : RotateCommand()
    }
}

typealias Forwards = Command.MoveCommand.Forwards
typealias Backwards = Command.MoveCommand.Backwards
typealias Right = Command.RotateCommand.Right
typealias Left = Command.RotateCommand.Left
