package be.swsb.coderetreat

import java.util.*


object VinValidator {
    private val transliterationMap =
                 listOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
            .zip(listOf( 1,   2,   3,   4,   5,   6,   7,   8,   0,   1,   2,   3,   4,   5,   0,   7,   0,   9,   2,   3,   4,   5,   6,   7,   8,   9,   0,   1,   2,   3,   4,   5,   6,   7,   8,   9))
            .toMap()
    private val weights = intArrayOf(8, 7, 6, 5, 4, 3, 2, 10, 0, 9, 8, 7, 6, 5, 4, 3, 2)

    fun validate(vinToValidate: String?): Optional<ValidationError> {
        val cleanedUpVin = vinToValidate.strippedOfDashesBlanksAndUppercased()
                ?: return Optional.of(ValidationError.from("VIN_MANDATORY"))
        if (cleanedUpVin.length != 17) return Optional.of(ValidationError.from("VIN_MAX_LENGTH"))
        if (cleanedUpVin.containsIllegalCharacters()) return Optional.of(ValidationError.from("VIN_ILLEGAL_CHARACTER"))
        if (cleanedUpVin.hasInvalidChecksum(transliterationMap, weights)) return Optional.of(ValidationError.from("VIN_ILLEGAL"))
        return Optional.empty<ValidationError>()
    }
}

private fun String?.strippedOfDashesBlanksAndUppercased() = this
        ?.replace("-".toRegex(), "")
        ?.replace(" ".toRegex(), "")
        ?.toUpperCase()
        ?.ifBlank { null }

private fun String.hasInvalidChecksum(transliterationMap: Map<Char, Int>, weights: IntArray): Boolean {
    fun isValidSpecialCase(mod: Int, checkCharacter: Char) = mod == 10 && checkCharacter == 'X'
    fun isValidTransliteration(mod: Int, checkCharacter: Char) = mod == checkCharacter.transliterate(transliterationMap)
    fun isValidChecksum(mod: Int, checkCharacter: Char) =
            isValidSpecialCase(mod, checkCharacter) || isValidTransliteration(mod, checkCharacter)

    val checkSum = this.foldIndexed(0) { i, acc, c ->
        val value = transliterationMap[c] ?: 0
        acc + weights[i] * value
    }
    val mod = checkSum % 11
    return !isValidChecksum(mod, this.getCheckCharacter())
}

private fun String.getCheckCharacter() = this[8]

private fun String.containsIllegalCharacters() = this.any { it.isIllegalCharacter() }
private fun Char.isIllegalCharacter() = this.isNotAlphaNumerical() || this in listOf('I', 'O', 'Q')
private fun Char.isAlphaNumerical() = "[A-Z0-9]*".toRegex().matches("$this")
private fun Char.isNotAlphaNumerical() = !this.isAlphaNumerical()
private fun Char.transliterate(transliterationMap: Map<Char, Int>) = transliterationMap[this]
