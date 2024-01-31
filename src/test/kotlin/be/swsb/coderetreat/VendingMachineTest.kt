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

    @Test
    fun `A VendingMachine displays total value of accepted coins`() {
        val vendingMachine = VendingMachine()
        vendingMachine.display().also { assertThat(it).isEqualTo("Insert Coin") }
        vendingMachine
            .insert(0.05.euro)
            .insert(0.10.euro)
            .insert(0.20.euro)
            .insert(0.50.euro)
            .insert(1.00.euro)
            .insert(2.00.euro)
            .display().also { assertThat(it).isEqualTo("3.85") }
    }


    @Test
    fun `A VendingMachine returns invalid coins`() {
        val vendingMachine = VendingMachine()
        vendingMachine.display().also { assertThat(it).isEqualTo("Insert Coin") }
        vendingMachine
            .insert(invalidCoin)
            .let {
                assertThat(it.display()).isEqualTo("Insert Coin")
                assertThat(it.returnBox).containsExactly(invalidCoin)
            }
        vendingMachine
            .insert(0.02.euro)
            .let {
                assertThat(it.display()).isEqualTo("Insert Coin")
                assertThat(it.returnBox).containsExactly(0.02.euro)
            }
        vendingMachine
            .insert(0.01.euro)
            .let {
                assertThat(it.display()).isEqualTo("Insert Coin")
                assertThat(it.returnBox).containsExactly(0.01.euro)
            }
        vendingMachine
            .insert(0.10.euro)
            .insert(invalidCoin)
            .let {
                assertThat(it.display()).isEqualTo("0.10")
                assertThat(it.returnBox).containsExactly(invalidCoin)
            }
    }
}

data class Coin(
    val diameterInMilliMeter: Double,
    val thicknessInMilliMeter: Double,
    val massInGrams: Double,
)


class VendingMachine(
    private val insertedCoins: List<Coin> = mutableListOf(),
    val returnBox: List<Coin> = mutableListOf(),
) {
    fun display(): String = when {
        insertedCoins.isEmpty() -> "Insert Coin"
        else -> insertedCoins.mapNotNull { it.value }.sum().display()
    }

    fun insert(coin: Coin): VendingMachine {
        val (newCoins, returnBox) = (insertedCoins + coin).partition { it.canBeAccepted() }
        return VendingMachine(newCoins, returnBox)
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

    private fun Coin.canBeAccepted() = this.value in listOf(2.00, 1.00, 0.50, 0.20, 0.10, 0.05)

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