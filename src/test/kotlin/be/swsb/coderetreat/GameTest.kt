package be.swsb.coderetreat

import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test

class GameTest {

    @Test
    fun `A Game of Battleship can only be started when there are 2 players`() {
        val game = Game()

        assertThatExceptionOfType(IllegalStateException::class.java)
            .isThrownBy { game.start() }
            .withMessage("You require 2 players to be able to play a game of Battleship")
    }
}

class Game {
    val playerOne: String? = null
    val playerTwo: String? = null

    fun start() {
        check(playerOne != null && playerTwo != null) {
            "You require 2 players to be able to play a game of Battleship"
        }
    }
}