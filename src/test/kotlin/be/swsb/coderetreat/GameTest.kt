package be.swsb.coderetreat

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GameTest {

    @Test
    fun `A Game of Battleship cannot be started without player names`() {
        val game = Game.start(playerOne = "", playerTwo = "")
        assertThat(game).isEqualTo(Game.start("Red", "Blue"))
    }
}

data class Game private constructor(
    private val playerOne: Player,
    private val playerTwo: Player,
) {

    companion object {
        fun start(playerOne: String, playerTwo: String) = Game(Player(playerOne, "Red"), Player(playerTwo, "Blue"))
    }
}

data class Player private constructor(val name: String) {
    companion object {
        operator fun invoke(name: String, default: String) = Player(name.ifBlank { default })
    }
}