package be.swsb.coderetreat

import java.util.*


object VinValidator {
    private val VALUES = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 0, 1, 2, 3, 4, 5, 0, 7, 0, 9, 2, 3, 4, 5, 6, 7, 8, 9)
    private val WEIGHTS = intArrayOf(8, 7, 6, 5, 4, 3, 2, 10, 0, 9, 8, 7, 6, 5, 4, 3, 2)

    fun validate(vinToValidate: String?): Optional<ValidationError> {
        if (vinToValidate == null || vinToValidate.isBlank()) {
            return Optional.of(ValidationError.from("VIN_MANDATORY"))
        }
        val vin = strippedToUppercase(vinToValidate)
        if (vin.length != 17) {
            return Optional.of(ValidationError.from("VIN_MAX_LENGTH"))
        }
        var sum = 0
        for (i in 0..16) {
            val c = vin[i]
            var value: Int
            // Only accept the 26 letters of the alphabet
            if (c in 'A'..'Z') {
                value = VALUES[c - 'A']
                if (value == 0) {
                    return Optional.of(ValidationError.from("VIN_ILLEGAL_CHARACTER"))
                }
            } else if (Character.isDigit(c)) {
                value = c - '0'
            } else {    // illegal character
                return Optional.of(ValidationError.from("VIN_ILLEGAL_CHARACTER"))
            }
            sum += WEIGHTS[i] * value
        }
        // check digit
        sum %= 11
        val check = vin[8]
        if (sum == 10 && check == 'X') {
            return Optional.empty<ValidationError>()
        }
        return if (sum == transliterate(check)) {
            Optional.empty<ValidationError>()
        } else Optional.of(ValidationError.from("VIN_ILLEGAL"))
    }

    private fun strippedToUppercase(value: String) = value
            .replace("-".toRegex(), "")
            .replace(" ".toRegex(), "")
            .toUpperCase()

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
