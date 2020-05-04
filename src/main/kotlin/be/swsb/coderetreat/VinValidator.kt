package be.swsb.coderetreat

import java.util.*


object VinValidator {
    private val WEIGHTS = intArrayOf(8, 7, 6, 5, 4, 3, 2, 10, 0, 9, 8, 7, 6, 5, 4, 3, 2)

    private val valueMap: Map<Char, Int> = (('A'..'Z') + ('0'..'9'))
            .zip(listOf(1, 2, 3, 4, 5, 6, 7, 8, 0, 1, 2, 3, 4, 5, 0, 7, 0, 9, 2, 3, 4, 5, 6, 7, 8, 9) + listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9))
            .toMap()

    fun validate(vinToValidate: String?): Optional<ValidationError> {
        val vin = vinToValidate.strippedToUppercase()
        if (vin.isNullOrBlank()) return Optional.of(ValidationError.from("VIN_MANDATORY"))
        if (vin.length != 17) return Optional.of(ValidationError.from("VIN_MAX_LENGTH"))

        if (vin.isNotAlphaNumerical()) return Optional.of(ValidationError.from("VIN_ILLEGAL_CHARACTER"))
        if (vin.containsOneOf('I', 'O', 'Q')) return Optional.of(ValidationError.from("VIN_ILLEGAL_CHARACTER"))

        return checkDigit(vin)
    }

    private fun checkDigit(vin: String): Optional<ValidationError> {
        val sum = vin.foldIndexed(0) { i, acc, c ->
            val value = valueMap[c] ?: 0
            acc + WEIGHTS[i] * value
        }
        val mod11 = sum % 11
        val ninethChar = vin[8]
        return if (specialCharacter(ninethChar, mod11) || mod11 == ninethChar.transliterate()) Optional.empty<ValidationError>()
        else Optional.of(ValidationError.from("VIN_ILLEGAL"))
    }

    private fun specialCharacter(check: Char, mod11: Int) = (mod11 == 10 && check == 'X')
}

fun String?.strippedToUppercase() = this
        ?.replace("-".toRegex(), "")
        ?.replace(" ".toRegex(), "")
        ?.toUpperCase()

fun String.isAlphaNumerical() = this.matches("^([A-Z0-9])*$".toRegex())
fun String.isNotAlphaNumerical() = !this.isAlphaNumerical()
fun String.containsOneOf(vararg c : Char) = this.any { it in c }

fun Char.transliterate() = when (this) {
    in listOf('A', 'J') -> 1
    in listOf('B', 'K', 'S') -> 2
    in listOf('C', 'L', 'T') -> 3
    in listOf('D', 'M', 'U') -> 4
    in listOf('E', 'N', 'V') -> 5
    in listOf('F', 'W') -> 6
    in listOf('G', 'P', 'X') -> 7
    in listOf('H', 'Y') -> 8
    in listOf('R', 'Z') -> 9
    else -> Character.getNumericValue(this)
}
