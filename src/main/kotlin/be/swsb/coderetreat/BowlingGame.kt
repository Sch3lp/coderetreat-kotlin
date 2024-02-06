package be.swsb.coderetreat

object Game {
    val frames: ListOfTen<Frame> = TODO()
}

class ListOfTen<T>
sealed class Frame {
    val score: Score = TODO()
    val firstRoll: FirstRoll = TODO()
}


@JvmInline
value class Score(val value: Int)
class FirstRoll : (KnockedPins) -> Frame {
    override fun invoke(knockedPins: KnockedPins) =
        when (knockedPins) {
            KnockedPins.`10` -> Strike()
            else -> RegularFrame()
        }
}

class SecondRoll : (KnockedPins) -> Frame {
    override fun invoke(knockedPins: KnockedPins): Frame =
        when (knockedPins) {
            KnockedPins.`10` -> Spare()
            else -> RegularFrame()
        }
}

class ThirdRoll

class Strike : Frame() // "X  "
class Spare : Frame() // "2 /"
class RegularFrame : Frame() {
    val secondRoll: SecondRoll = TODO()
}

class FinalFrame : Frame() {
    val secondRoll: SecondRoll = TODO()
    val thirdRoll: ThirdRoll = TODO()
}

enum class KnockedPins {
    `10`,
    `9`,
    `8`,
    `7`,
    `6`,
    `5`,
    `4`,
    `3`,
    `2`,
    `1`,
    `0`,
}

// class
// enum
// value class
// data class
// object
// interface
// generics
