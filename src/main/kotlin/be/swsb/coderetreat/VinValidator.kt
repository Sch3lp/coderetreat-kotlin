package be.swsb.coderetreat

import java.util.*


object VinValidator {
    //private val ALPHA  =                    A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z
    private val alphabetValueMap = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 0, 1, 2, 3, 4, 5, 0, 7, 0, 9, 2, 3, 4, 5, 6, 7, 8, 9)
    private val WEIGHTS = intArrayOf(8, 7, 6, 5, 4, 3, 2, 10, 0, 9, 8, 7, 6, 5, 4, 3, 2)

    fun validate(vinToValidate: String?): Optional<ValidationError> {
        val cleanedUpVin = vinToValidate.strippedOfDashesBlanksAndUppercased() ?: return Optional.of(ValidationError.from("VIN_MANDATORY"))
        if (cleanedUpVin.length != 17) return Optional.of(ValidationError.from("VIN_MAX_LENGTH"))
        if (cleanedUpVin.containsIllegalCharacters()) return Optional.of(ValidationError.from("VIN_ILLEGAL_CHARACTER"))

        var sum = 0
        for (i in 0..16) {
            val c = cleanedUpVin[i]
            var value: Int
            // Only accept the 26 letters of the alphabet
            if (c in 'A'..'Z') {
                value = alphabetValueMap[c - 'A']
            } else if (Character.isDigit(c)) {
                value = c - '0'
            } else {    // illegal character
                value = 0
            }
            sum = sum + WEIGHTS[i] * value
        }
        // check digit
        sum = sum % 11
        val check = cleanedUpVin[8]
        if (sum == 10 && check == 'X') {
            return Optional.empty<ValidationError>()
        }
        return if (sum == transliterate(check)) {
            Optional.empty<ValidationError>()
        } else Optional.of(ValidationError.from("VIN_ILLEGAL"))
    }

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

private fun String?.strippedOfDashesBlanksAndUppercased() = this
        ?.replace("-".toRegex(), "")
        ?.replace(" ".toRegex(), "")
        ?.toUpperCase()
        ?.ifBlank { null }
private fun String.containsIllegalCharacters() = this.any { it.isIllegalCharacter() }
private fun Char.isAlphaNumerical() = "[A-Z0-9]*".toRegex().matches("$this")
private fun Char.isNotAlphaNumerical() = !this.isAlphaNumerical()
private fun Char.isIllegalCharacter() = this.isNotAlphaNumerical() || this in listOf('I','O','Q')
