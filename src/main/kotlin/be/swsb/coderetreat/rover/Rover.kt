package be.swsb.coderetreat.rover

import be.swsb.coderetreat.planet.Mars
import be.swsb.coderetreat.planet.Planet

data class Rover(val facingDirection: Direction = Direction.NORTH,
                 val position: Position = Position(0, 0),
                 val planet: Planet = Mars) {

    fun receiveCommands(commands: List<RoverCommand>): Rover {
        return commands.fold(this, Rover::receiveCommand)
    }

    fun receiveCommand(command: RoverCommand): Rover {
        return when (command) {
            is RoverCommand.MoveCommand -> move(debug(command))
            is RoverCommand.RotateCommand -> rotate(debug(command))
        }
    }

    // 2nd Refactor task:
    // Pass MovingDirection to an Edge (new sealed class) to either wrap or not
    private fun move(moveCommand: RoverCommand.MoveCommand): Rover {
        val movingDirection = moveCommand.movingDirection(facingDirection)

        val stepDirection = when (movingDirection) {
            Direction.NORTH -> StepDirection.UP
            Direction.SOUTH -> StepDirection.DOWN
            Direction.EAST -> StepDirection.UP
            Direction.WEST -> StepDirection.DOWN
        }

        val newPosition = when (movingDirection) {
            Direction.NORTH -> position.stepY(stepDirection)
            Direction.EAST -> position.stepX(stepDirection)
            Direction.SOUTH -> position.stepY(stepDirection)
            Direction.WEST -> position.stepX(stepDirection)
        }

        //check if newPosition exceeds the planet's edge, and flip either x or y
        val wrappedPosition = wrapPositionIfNecessary(newPosition)
        return debug(Rover(facingDirection = facingDirection, position = wrappedPosition, planet = planet))
    }

    private fun wrapPositionIfNecessary(newPosition: Position): Position {
        return when (facingDirection) {
            Direction.NORTH -> flipYWhenOnEdge(newPosition)
            Direction.SOUTH -> flipYWhenOnEdge(newPosition)
            Direction.EAST -> flipXWhenOnEdge(newPosition)
            Direction.WEST -> flipXWhenOnEdge(newPosition)
        }
    }

    private fun flipXWhenOnEdge(newPosition: Position): Position {
        return if (planet.isAnEdge(position)) {
            position.flipX()
        } else {
            newPosition
        }
    }

    private fun flipYWhenOnEdge(newPosition: Position): Position {
        return if (planet.isAnEdge(position)) {
            position.flipY()
        } else {
            newPosition
        }
    }

    private fun rotate(rotateCommand: RoverCommand.RotateCommand): Rover {
        val newDirection = when (rotateCommand) {
            is Right -> facingDirection.rotateClockwise()
            is Left -> facingDirection.rotateCounterClockwise()
        }
        return debug(Rover(facingDirection = newDirection, position = position, planet = planet))
    }

    private fun <T> debug(any: T): T {
        println(any)
        return any
    }
}

sealed class RoverCommand {
    sealed class MoveCommand : RoverCommand() {
        object Forwards : MoveCommand()
        object Backwards : MoveCommand()

        fun movingDirection(facingDirection: Direction): MovingDirection {
            return when(this) {
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
