package be.swsb.coderetreat.planet

data class Dimension(val height: Int, val width: Int)

fun `5x5`() : Dimension {
    return Dimension(5, 5)
}

fun `3x3`() : Dimension {
    return Dimension(3, 3)
}