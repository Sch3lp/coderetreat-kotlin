package be.swsb.coderetreat

import java.util.*


object VinValidator {
    private val actualMap = listOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
            .zip(listOf(1, 2, 3, 4, 5, 6, 7, 8, 0, 1, 2, 3, 4, 5, 0, 7, 0, 9, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9))
            .toMap()
    private val weights = intArrayOf(8, 7, 6, 5, 4, 3, 2, 10, 0, 9, 8, 7, 6, 5, 4, 3, 2)

    fun validate(vinToValidate: String?): Optional<ValidationError> {
        val cleanedUpVin = vinToValidate.strippedOfDashesBlanksAndUppercased()
                ?: return Optional.of(ValidationError.from("VIN_MANDATORY"))
        if (cleanedUpVin.length != 17) return Optional.of(ValidationError.from("VIN_MAX_LENGTH"))
        if (cleanedUpVin.containsIllegalCharacters()) return Optional.of(ValidationError.from("VIN_ILLEGAL_CHARACTER"))
        if (cleanedUpVin.hasInvalidChecksum(actualMap, weights)) return Optional.of(ValidationError.from("VIN_ILLEGAL"))
        return Optional.empty<ValidationError>()
    }
}

private fun String?.strippedOfDashesBlanksAndUppercased() = this
        ?.replace("-".toRegex(), "")
        ?.replace(" ".toRegex(), "")
        ?.toUpperCase()
        ?.ifBlank { null }
private fun String.hasInvalidChecksum(actualMap: Map<Char, Int>, weights: IntArray): Boolean {
    fun isValidSpecialCase(mod: Int, checkCharacter: Char) = mod == 10 && checkCharacter == 'X'
    fun isValidTransliteration(mod: Int, checkCharacter: Char) = mod == checkCharacter.transliterate()
    fun isValidChecksum(mod: Int, checkCharacter: Char) =
            isValidSpecialCase(mod, checkCharacter) || isValidTransliteration(mod, checkCharacter)

    val checkSum = this.foldIndexed(0) { i, acc, c ->
        val value = actualMap[c] ?: 0
        acc + weights[i] * value
    }
    val mod = checkSum % 11
    return !isValidChecksum(mod, this.getCheckCharacter())
}
private fun String.getCheckCharacter() = this[8]

private fun String.containsIllegalCharacters() = this.any { it.isIllegalCharacter() }
private fun Char.isAlphaNumerical() = "[A-Z0-9]*".toRegex().matches("$this")
private fun Char.isNotAlphaNumerical() = !this.isAlphaNumerical()
private fun Char.isIllegalCharacter() = this.isNotAlphaNumerical() || this in listOf('I', 'O', 'Q')
private fun Char.transliterate(): Int {
    if (this == 'A' || this == 'J') {
        return 1
    } else if (this == 'B' || this == 'K' || this == 'S') {
        return 2
    } else if (this == 'C' || this == 'L' || this == 'T') {
        return 3
    } else if (this == 'D' || this == 'M' || this == 'U') {
        return 4
    } else if (this == 'E' || this == 'N' || this == 'V') {
        return 5
    } else if (this == 'F' || this == 'W') {
        return 6
    } else if (this == 'G' || this == 'P' || this == 'X') {
        return 7
    } else if (this == 'H' || this == 'Y') {
        return 8
    } else if (this == 'R' || this == 'Z') {
        return 9
    } else if (Integer.valueOf(Character.getNumericValue(this)) != null) { //hacky but works
        return Character.getNumericValue(this)
    }
    return -1
}
