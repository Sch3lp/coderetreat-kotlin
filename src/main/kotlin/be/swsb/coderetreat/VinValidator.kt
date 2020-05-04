package be.swsb.coderetreat

import java.util.*


object VinValidator {
    private val WEIGHTS = intArrayOf(8, 7, 6, 5, 4, 3, 2, 10, 0, 9, 8, 7, 6, 5, 4, 3, 2)

    //TODO: I, O and Q get 0 as value, and this makes them return VIN_ILLEGAL_CHARACTER
    private val valueMap: Map<Char, Int> = (('A'..'Z') + ('0'..'9'))
            .zip(listOf(1, 2, 3, 4, 5, 6, 7, 8, 0, 1, 2, 3, 4, 5, 0, 7, 0, 9, 2, 3, 4, 5, 6, 7, 8, 9) + listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9))
            .toMap()

    fun validate(vinToValidate: String?): Optional<ValidationError> {
        val vin = strippedToUppercase(vinToValidate)
        if (vin.isNullOrBlank()) return Optional.of(ValidationError.from("VIN_MANDATORY"))
        if (vin.length != 17) return Optional.of(ValidationError.from("VIN_MAX_LENGTH"))

        if (!vin.matches("([A-Z0-9])*".toRegex())) return Optional.of(ValidationError.from("VIN_ILLEGAL_CHARACTER"))
        if (vin.containsOneOf('I', 'O', 'Q')) return Optional.of(ValidationError.from("VIN_ILLEGAL_CHARACTER"))

        var sum = vin.foldIndexed(0) { i, acc, c ->
            val value = valueMap[c] ?: 0
            acc + WEIGHTS[i] * value
        }

        // check digit
        sum %= 11
        val check = vin[8] //TODO is there never an I on the 8th index?
        if (sum == 10 && check == 'X') {
            return Optional.empty<ValidationError>()
        }
        return if (sum == transliterate(check)) {
            Optional.empty<ValidationError>()
        } else Optional.of(ValidationError.from("VIN_ILLEGAL"))
    }

    private fun strippedToUppercase(value: String?) = value
            ?.replace("-".toRegex(), "")
            ?.replace(" ".toRegex(), "")
            ?.toUpperCase()

    private fun transliterate(c: Char) = when (c) {
        in listOf('A', 'J') -> 1
        in listOf('B', 'K', 'S') -> 2
        in listOf('C', 'L', 'T') -> 3
        in listOf('D', 'M', 'U') -> 4
        in listOf('E', 'N', 'V') -> 5
        in listOf('F', 'W') -> 6
        in listOf('G', 'P', 'X') -> 7
        in listOf('H', 'Y') -> 8
        in listOf('R', 'Z') -> 9
        else -> Character.getNumericValue(c)
    }
}

fun String.containsOneOf(vararg c : Char) = this.any { it in c }