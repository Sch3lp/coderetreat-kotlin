package be.swsb.coderetreat

import java.util.*


object VinValidator {
    private val VALUES = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 0, 1, 2, 3, 4, 5, 0, 7, 0, 9, 2, 3, 4, 5, 6, 7, 8, 9)
    private val WEIGHTS = intArrayOf(8, 7, 6, 5, 4, 3, 2, 10, 0, 9, 8, 7, 6, 5, 4, 3, 2)

    fun validate(vinToValidate: String?): Optional<ValidationError> {
        if (vinToValidate == null || vinToValidate.isBlank()) {
            return Optional.of(ValidationError.from("VIN_MANDATORY"))
        }
        val vin = cleanUpVin(vinToValidate)
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

    private fun cleanUpVin(value: String) = value
            .replace("-".toRegex(), "")
            .replace(" ".toRegex(), "")
            .toUpperCase()

    private fun transliterate(c: Char) = when {
        c in listOf('A', 'J') -> 1
        c in listOf('B', 'K', 'S') -> 2
        c in listOf('C', 'L', 'T') -> 3
        c in listOf('D', 'M', 'U') -> 4
        c in listOf('E', 'N', 'V') -> 5
        c in listOf('F', 'W') -> 6
        c in listOf('G', 'P', 'X') -> 7
        c in listOf('H', 'Y') -> 8
        c in listOf('R', 'Z') -> 9
        Integer.valueOf(Character.getNumericValue(c)) != null -> Character.getNumericValue(c) //hacky but works
        else -> -1
    }
}
