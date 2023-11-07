package be.swsb.coderetreat

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass

class GameTest {

    @Test
    fun `A Game of Battleship cannot be started without player names`() {
        val game = Game.start(playerOne = "", playerTwo = "")
        assertThat(game).isEqualTo(Game.start("Red", "Blue"))
    }

    @Test
    fun `A Game of Battleship requires both players to have placed all their ships before they can start firing at each other`() {
        val game = Game.start("Bruce", "Selina")
        assertThatExceptionOfType(IllegalStateException::class.java)
            .isThrownBy { game.fire(target = Player2::class) }
    }
}

data class Game private constructor(
    val playerOne: Player,
    val playerTwo: Player,
) {
    fun fire(target: KClass<out Player>): Game {
        return this
    }

    companion object {
        fun start(playerOne: String, playerTwo: String) = Game(Player1(playerOne), Player2(playerTwo))
    }
}

sealed class Player(name: String, default: String) {
    val name: String = name.ifBlank { default }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Player

        return name == other.name
    }
    override fun hashCode(): Int {
        return name.hashCode()
    }
}

class Player1(name: String) : Player(name, "Red")
class Player2(name: String) : Player(name, "Blue")