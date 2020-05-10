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
            if (c >= 'A' && c <= 'Z') {
                value = VALUES[c - 'A']
                if (value == 0) {
                    return Optional.of(ValidationError.from("VIN_ILLEGAL_CHARACTER"))
                }
            } else if (Character.isDigit(c)) {
                value = c - '0'
            } else {    // illegal character
                return Optional.of(ValidationError.from("VIN_ILLEGAL_CHARACTER"))
            }
            sum = sum + WEIGHTS[i] * value
        }
        // check digit
        sum = sum % 11
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

    private fun transliterate(check: Char): Int {
        if (check == 'A' || check == 'J') {
            return 1
        } else if (check == 'B' || check == 'K' || check == 'S') {
            return 2
        } else if (check == 'C' || check == 'L' || check == 'T') {
            return 3
        } else if (check == 'D' || check == 'M' || check == 'U') {
            return 4
        } else if (check == 'E' || check == 'N' || check == 'V') {
            return 5
        } else if (check == 'F' || check == 'W') {
            return 6
        } else if (check == 'G' || check == 'P' || check == 'X') {
            return 7
        } else if (check == 'H' || check == 'Y') {
            return 8
        } else if (check == 'R' || check == 'Z') {
            return 9
        } else if (Integer.valueOf(Character.getNumericValue(check)) != null) { //hacky but works
            return Character.getNumericValue(check)
        }
        return -1
    }
}
