package be.swsb.coderetreat

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*


class VendingMachineTest {

    @Test
    fun `A VendingMachine can accept valid coins`() {
        val vendingMachine = VendingMachine()
        vendingMachine.display().also { assertThat(it).isEqualTo("Insert Coin") }
        vendingMachine
            .insert(0.05.euro)
            .display().also { assertThat(it).isEqualTo("0.05") }
        vendingMachine
            .insert(0.10.euro)
            .display().also { assertThat(it).isEqualTo("0.10") }
        vendingMachine
            .insert(0.20.euro)
            .display().also { assertThat(it).isEqualTo("0.20") }
        vendingMachine
            .insert(0.50.euro)
            .display().also { assertThat(it).isEqualTo("0.50") }
        vendingMachine
            .insert(1.00.euro)
            .display().also { assertThat(it).isEqualTo("1.00") }
        vendingMachine
            .insert(2.00.euro)
            .display().also { assertThat(it).isEqualTo("2.00") }
    }
}

data class Coin(
    val diameterInMilliMeter: Double,
    val thicknessInMilliMeter: Double,
    val massInGrams: Double,
)


class VendingMachine(private val coins: List<Coin> = mutableListOf()) {
    fun display(): String = when {
        coins.isEmpty() -> "Insert Coin"
        else -> coins.mapNotNull { it.value }.sum().display()
    }

    fun insert(coin: Coin): VendingMachine {
        return VendingMachine(coins + coin)
    }

    private val Coin.value
        get() = when (this) {
            Coin(21.25, 1.67, 3.92) -> 0.05
            Coin(19.75, 1.93, 4.10) -> 0.10
            Coin(22.25, 2.14, 5.74) -> 0.20
            Coin(24.25, 2.38, 7.80) -> 0.50
            Coin(23.25, 2.33, 7.50) -> 1.00
            Coin(25.75, 2.20, 8.50) -> 2.00
            else -> null
        }

    private fun Double.display() = String.format(Locale.US, "%.2f", this)
}


private val Double.euro
    get() = when (this) {
        0.01 -> Coin(16.25, 1.67, 2.30)
        0.02 -> Coin(18.75, 1.67, 3.06)
        0.05 -> Coin(21.25, 1.67, 3.92)
        0.10 -> Coin(19.75, 1.93, 4.10)
        0.20 -> Coin(22.25, 2.14, 5.74)
        0.50 -> Coin(24.25, 2.38, 7.80)
        1.0 -> Coin(23.25, 2.33, 7.50)
        2.0 -> Coin(25.75, 2.20, 8.50)
        else -> invalidCoin
    }


private val invalidCoin = Coin(100.1, 101.5, 203.9)