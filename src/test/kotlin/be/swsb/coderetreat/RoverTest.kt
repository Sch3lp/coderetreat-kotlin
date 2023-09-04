package be.swsb.coderetreat

import be.swsb.coderetreat.Command.*
import be.swsb.coderetreat.Direction.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.of
import org.junit.jupiter.params.provider.MethodSource
import kotlin.random.Random

class RoverTest {

    @Test
    fun `A Rover stops executing when it encountered an obstacle`() {
        val scanner = RandomScanner(Random(5))
        val initialRover = Rover(scanner = scanner) //Will scan something on the 5th move

        val actual = initialRover.receiveCommands(Forwards, Forwards, Forwards, Forwards, Forwards, Forwards, Backwards)

        assertThat(actual).isEqualTo(Rover(scanner = scanner, at = at(0, 4), ignoringCommands = true))
    }

    @ParameterizedTest
    @MethodSource("forwardsCriteria")
    fun `A Rover can Move forwards`(startingPosition: Position, facing: Direction, expectedPosition: Position) {
        val initialRover = Rover(facing = facing, at = startingPosition)
        val actual = initialRover.receiveCommands(Forwards)
        val expectedRover = Rover(facing = facing, at = expectedPosition)
        assertThat(actual).isEqualTo(expectedRover)
    }

    @ParameterizedTest
    @MethodSource("backwardsCriteria")
    fun `A Rover can Move backwards`(startingPosition: Position, facing: Direction, expectedPosition: Position) {
        val initialRover = Rover(facing = facing, at = startingPosition)
        val actual = initialRover.receiveCommands(Backwards)
        val expectedRover = Rover(facing = facing, at = expectedPosition)
        assertThat(actual).isEqualTo(expectedRover)
    }

    @Test
    fun `A Rover can turn right and left`() {
        val rover = Rover(facing = North)
        rover.receiveCommands(Right)
            .also { assertThat(it).isEqualTo(Rover(East)) }
            .receiveCommands(Right)
            .also { assertThat(it).isEqualTo(Rover(South)) }
            .receiveCommands(Right)
            .also { assertThat(it).isEqualTo(Rover(West)) }
            .receiveCommands(Right)
            .also { assertThat(it).isEqualTo(Rover(North)) }
            .receiveCommands(Left)
            .also { assertThat(it).isEqualTo(Rover(West)) }
            .receiveCommands(Left)
            .also { assertThat(it).isEqualTo(Rover(South)) }
            .receiveCommands(Left)
            .also { assertThat(it).isEqualTo(Rover(East)) }
            .receiveCommands(Left)
            .also { assertThat(it).isEqualTo(Rover(North)) }
    }

    companion object {
        @JvmStatic
        fun forwardsCriteria() = listOf(
            of(at(10, 10), North, at(10, 11)),
            of(at(10, 10), East, at(11, 10)),
            of(at(10, 10), South, at(10, 9)),
            of(at(10, 10), West, at(9, 10)),
        )

        @JvmStatic
        fun backwardsCriteria() = listOf(
            of(at(10, 10), North, at(10, 9)),
            of(at(10, 10), East, at(9, 10)),
            of(at(10, 10), South, at(10, 11)),
            of(at(10, 10), West, at(11, 10)),
        )
    }
}

data class Rover(
    private val facing: Direction = North,
    private val at: Position = at(0, 0),
    private val scanner: Scanner = RandomScanner(),
    private val ignoringCommands: Boolean = false,
) {

    fun receiveCommands(vararg commands: Command): Rover =
        commands.fold(this) { acc, command -> acc.executeCommand(command) }

    private fun executeCommand(command: Command): Rover = viaCircuitBreaker {
        when (command) {
            Forwards -> copy(at =this.at + facing.vector)
            Backwards -> copy(at = this.at - facing.vector)
            Right -> copy(facing = facing.clockwise())
            Left -> copy(facing = facing.counterClockwise())
        }
    }

    private fun viaCircuitBreaker(commandBlock: () -> Rover): Rover {
        if (ignoringCommands) return this
        val newRover = commandBlock()
        return if (scanner.scan(newRover.at) == null) newRover
        else copy(ignoringCommands = true)
    }
}


data class Position(private val x: Int, private val y: Int) {
    operator fun plus(other: Position) = Position(this.x + other.x, this.y + other.y)
    operator fun minus(other: Position) = Position(this.x - other.x, this.y - other.y)
}

fun at(x: Int, y: Int) = Position(x, y)

sealed class Direction(
    val vector: Position,
    val clockwise: () -> Direction,
    val counterClockwise: () -> Direction,
) {

    data object North : Direction(Position(0, 1), clockwise = { East }, counterClockwise = { West })
    data object East : Direction(Position(1, 0), clockwise = { South }, counterClockwise = { North })
    data object South : Direction(Position(0, -1), clockwise = { West }, counterClockwise = { East })
    data object West : Direction(Position(-1, 0), clockwise = { North }, counterClockwise = { South })
}

enum class Command {
    Forwards,
    Backwards,
    Right,
    Left,
}


enum class Obstacle {
    Crater,
    Martian,
}

interface Scanner {
    fun scan(position: Position): Obstacle?
}

data class RandomScanner(private val random: Random = Random) : Scanner {
    override fun scan(position: Position): Obstacle? =
        random.nextInt(1, 20).let {
            when (it) {
                1 -> Obstacle.Crater
                2 -> Obstacle.Martian
                else -> null
            }
        }
}