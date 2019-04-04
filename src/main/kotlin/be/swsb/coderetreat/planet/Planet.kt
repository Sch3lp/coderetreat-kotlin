package be.swsb.coderetreat.planet

data class Planet(val dimension: Dimension) {
    companion object {
        fun mars(): Planet {
            return Planet(`4 x 4`())
        }
    }
}

fun `4 x 4`() : Dimension {
    return Dimension(4, 4)
}