package be.swsb.coderetreat

import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test

class GameTest {

    @Test
    fun `A Game of Battleship can only be started when there are 2 players`() {
        val game = Game()

        assertThatExceptionOfType(IllegalStateException::class.java)
            .isThrownBy { game.start() }
    }
}

class Game {
    fun start() {
        TODO("Not yet implemented")
    }
}