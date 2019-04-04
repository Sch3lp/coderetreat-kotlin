package be.swsb.coderetreat.planet

data class Dimension(val height: Int, val width: Int)

fun `4x4`() : Dimension {
    return Dimension(4, 4)
}

fun `3x3`() : Dimension {
    return Dimension(3, 3)
}