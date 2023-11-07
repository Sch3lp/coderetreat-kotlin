package be.swsb.coderetreat

import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.assertj.core.api.Assertions.assertThatNoException
import org.junit.jupiter.api.Test

class GameTest {

    @Test
    fun `A Game of Battleship can only be started when there are 2 players`() {
        val game = Game()

        assertThatExceptionOfType(IllegalStateException::class.java)
            .isThrownBy { game.start() }
            .withMessage("You require 2 players to be able to play a game of Battleship")

        game.ready(playerOne = "Bruce", playerTwo = "Selina")

        assertThatNoException()
            .isThrownBy { game.start() }
    }
}

class Game {
    var playerOne: String? = null
        private set
    var playerTwo: String? = null
        private set

    fun start() {
        check(playerOne != null && playerTwo != null) {
            "You require 2 players to be able to play a game of Battleship"
        }
    }

    fun ready(playerOne: String, playerTwo: String) {
        this.playerOne = playerOne
        this.playerTwo = playerTwo
    }
}