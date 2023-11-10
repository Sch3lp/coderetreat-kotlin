package be.swsb.coderetreat

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.properties.Delegates

class GameEventsTest {
    @Test
    fun name() {
        var brol: String = ""
        val gameEventz = GameEventz()
        gameEventz.hook(object: GameEventListener {
                override fun receive(event: String) {
                    brol = event
                }
            })
        gameEventz.broadcast("fuck")

        assertThat(brol).isEqualTo("fuck")
    }
}

class GameEventz(events: List<String> = emptyList()) {
    private lateinit var listener: GameEventListener
    private val _events = events.toMutableList()

    fun hook(turnOrder: GameEventListener) {
        this.listener = turnOrder
    }

    fun broadcast(event: String) {
        gameEvents += event
    }

    val gameEvents by Delegates.observable(_events) { _, old, new ->
        if (old != new) {
            listener.receive((new - old).first())
        }
    }
}
