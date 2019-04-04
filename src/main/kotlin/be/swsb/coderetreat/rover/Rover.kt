package be.swsb.coderetreat.rover

import be.swsb.coderetreat.planet.Planet

data class Rover(val facingDirection: Direction = Direction.NORTH,
                 val position: Position = Position(0, 0),
                 val planet: Planet = Planet.mars()) {

    fun receiveCommands(commands: List<RoverCommand>): Rover {
        return commands.fold(this, Rover::receiveCommand)
    }

    fun receiveCommand(command: RoverCommand): Rover {
        return when (command) {
            is RoverCommand.MoveCommand -> move(command)
            is RoverCommand.RotateCommand -> rotate(command)
        }
    }

    private fun move(moveCommand: RoverCommand.MoveCommand): Rover {
        val stepDirection = when (moveCommand) {
            is Forwards -> StepDirection.UP
            is Backwards -> StepDirection.DOWN
        }

        val newPosition = when (facingDirection) {
            Direction.NORTH -> position.stepY(stepDirection)
            Direction.EAST -> position.stepX(stepDirection)
            Direction.SOUTH -> position.stepY(stepDirection.flip())
            Direction.WEST -> position.stepX(stepDirection.flip())
        }

        //check if newPosition exceeds the planet's edge, and flip either x or y
        val wrappedPosition = if (planet.isAnEdge(position)) {
            position.flipY()
        } else {
            newPosition
        }

        return Rover(facingDirection = facingDirection, position = wrappedPosition, planet = planet)
    }

    private fun rotate(rotateCommand: RoverCommand.RotateCommand): Rover {
        val newDirection = when (rotateCommand) {
            is Right -> facingDirection.rotateClockwise()
            is Left  -> facingDirection.rotateCounterClockwise()
        }
        return Rover(facingDirection = newDirection, position = position, planet = planet)
    }
}


sealed class RoverCommand {
    sealed class MoveCommand : RoverCommand() {
        object Forwards : MoveCommand()
        object Backwards : MoveCommand()
    }

    sealed class RotateCommand : RoverCommand() {
        object Right : RotateCommand()
        object Left : RotateCommand()
    }
}

typealias Forwards = RoverCommand.MoveCommand.Forwards
typealias Backwards = RoverCommand.MoveCommand.Backwards
typealias Right = RoverCommand.RotateCommand.Right
typealias Left = RoverCommand.RotateCommand.Left
