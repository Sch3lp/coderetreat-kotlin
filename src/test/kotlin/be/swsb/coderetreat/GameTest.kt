package be.swsb.coderetreat

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

class GameTest {

    @Test
    fun `A Game of Battleship cannot be started without player names`() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException::class.java)
            .isThrownBy { Game.start(playerOne = "Bruce", playerTwo = "") }
    }
}

class Game private constructor(
    private val playerOne: Player,
    private val playerTwo: Player,
) {

    companion object {
        fun start(playerOne: String, playerTwo: String) = Game(Player(playerOne), Player(playerTwo))
    }
}

data class Player(val name: String) {
    init {
        require(name.isNotBlank()) { "Cannot have blank as a player name" }
    }
}