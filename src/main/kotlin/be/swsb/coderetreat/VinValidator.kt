package be.swsb.coderetreat

import java.util.*


object VinValidator {
    private val transliterationMap = TransliterationMap.vinValidationMap()
    private val weights = intArrayOf(8, 7, 6, 5, 4, 3, 2, 10, 0, 9, 8, 7, 6, 5, 4, 3, 2)

    fun validate(vinToValidate: String?): Optional<ValidationError> {
        val cleanedUpVin = vinToValidate.strippedOfDashesBlanksAndUppercased()
                ?: return Optional.of(ValidationError.from("VIN_MANDATORY"))
        if (cleanedUpVin.length != 17) return Optional.of(ValidationError.from("VIN_MAX_LENGTH"))
        if (cleanedUpVin.containsIllegalCharacters(transliterationMap)) return Optional.of(ValidationError.from("VIN_ILLEGAL_CHARACTER"))
        if (cleanedUpVin.hasInvalidChecksum(transliterationMap, weights)) return Optional.of(ValidationError.from("VIN_ILLEGAL"))
        return Optional.empty<ValidationError>()
    }
}

private fun String?.strippedOfDashesBlanksAndUppercased() = this
        ?.replace("-".toRegex(), "")
        ?.replace(" ".toRegex(), "")
        ?.toUpperCase()
        ?.ifBlank { null }

private fun String.hasInvalidChecksum(transliterationMap: TransliterationMap, weights: IntArray): Boolean {
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

private fun String.containsIllegalCharacters(transliterationMap: TransliterationMap) = this.any { it.isIllegalCharacter(transliterationMap) }
private fun Char.isIllegalCharacter(transliterationMap: TransliterationMap) = this.isNotAlphaNumerical() || this in transliterationMap.illegalCharacters()
private fun Char.isAlphaNumerical() = "[A-Z0-9]*".toRegex().matches("$this")
private fun Char.isNotAlphaNumerical() = !this.isAlphaNumerical()
private fun Char.transliterate(transliterationMap: TransliterationMap) = transliterationMap[this]

data class TransliterationMap private constructor(private val _internalMap: Map<Char, Int>): Map<Char, Int> by _internalMap {

    fun illegalCharacters() = listOf('I', 'O', 'Q')

    companion object {
        fun vinValidationMap(): TransliterationMap = TransliterationMap(
                listOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
           .zip(listOf( 1,   2,   3,   4,   5,   6,   7,   8,   0,   1,   2,   3,   4,   5,   0,   7,   0,   9,   2,   3,   4,   5,   6,   7,   8,   9,   0,   1,   2,   3,   4,   5,   6,   7,   8,   9))
           .toMap()
        )
    }
}

