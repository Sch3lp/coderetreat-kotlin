package be.swsb.coderetreat

import org.junit.jupiter.api.Test

class GameTest {

    @Test
    fun `A Game of Battleship can only be started when there are 2 players`() {
        Game.start(playerOne = "Bruce", playerTwo = "Selina")
    }
}

class Game private constructor(
    private val playerOne: String,
    private val playerTwo: String,
) {

    companion object {
        fun start(playerOne: String, playerTwo: String) = Game(playerOne, playerTwo)
    }
}