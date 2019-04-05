package be.swsb.coderetreat.rover

import be.swsb.coderetreat.planet.Mars
import be.swsb.coderetreat.planet.Planet

data class Rover(val facingDirection: Direction = Direction.NORTH,
                 val position: Position = Position(0, 0),
                 val planet: Planet = Mars(),
                 val message: String? = null) {

    fun receiveCommands(commands: List<RoverCommand>): Rover {
        return commands.fold(this, Rover::receiveCommand)
    }

    fun receiveCommand(command: RoverCommand): Rover {
        return when (command) {
            is RoverCommand.MoveCommand -> move(debug(command))
            is RoverCommand.RotateCommand -> rotate(debug(command))
        }
    }

    private fun move(moveCommand: RoverCommand.MoveCommand): Rover {
        val movingDirection = moveCommand.movingDirection(facingDirection)

        val newPosition = moveToNewPosition(movingDirection)

        //check if newPosition exceeds the planet's edge, and flip either x or y
        val wrappedPosition = wrapPositionIfNecessary(newPosition, movingDirection)

        val message = checkForObstacleAt(wrappedPosition)

        return debug(Rover(facingDirection = facingDirection, position = wrappedPosition, planet = planet, message = message))
    }

    private fun checkForObstacleAt(aPosition: Position): String? {
        return if (planet.hasObstacleAt(aPosition)) {
            "There is an obstacle at ${aPosition.asString()}, ignoring further commands."
        } else {
            null
        }
    }

    private fun moveToNewPosition(movingDirection: MovingDirection): Position {
        return when (movingDirection) {
            Direction.NORTH -> position.stepY(StepDirection.UP)
            Direction.EAST -> position.stepX(StepDirection.UP)
            Direction.SOUTH -> position.stepY(StepDirection.DOWN)
            Direction.WEST -> position.stepX(StepDirection.DOWN)
        }
    }

    private fun wrapPositionIfNecessary(newPosition: Position, movingDirection: MovingDirection): Position {
        return planet.wrapWhenCrossingEdge(position, movingDirection) ?: newPosition
    }

    private fun rotate(rotateCommand: RoverCommand.RotateCommand): Rover {
        val newDirection = when (rotateCommand) {
            is Right -> facingDirection.rotateClockwise()
            is Left -> facingDirection.rotateCounterClockwise()
        }
        return debug(Rover(facingDirection = newDirection, position = position, planet = planet))
    }

    private fun <T> debug(any: T): T {
//        println(any)
        return any
    }
}

sealed class RoverCommand {
    sealed class MoveCommand : RoverCommand() {
        object Forwards : MoveCommand()
        object Backwards : MoveCommand()

        fun movingDirection(facingDirection: Direction): MovingDirection {
            return when (this) {
                is be.swsb.coderetreat.rover.Forwards -> facingDirection
                is be.swsb.coderetreat.rover.Backwards -> facingDirection.flip()
            }
        }
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
