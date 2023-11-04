package be.swsb.coderetreat

data class Point(val x: Int, val y: Int) {
    operator fun rangeTo(other: Point): List<Point> {
        return when {
            this.y == other.y -> {
                val pointsBetween = if (this.x > other.x) this.x - other.x else other.x - this.x
                (this.x..this.x + pointsBetween).map { this.copy(x = it) }
            }

            this.x == other.x -> {
                val pointsBetween = if (this.y > other.y) this.y - other.y else other.y - this.y
                (this.y..this.y + pointsBetween).map { this.copy(y = it) }
            }

            else -> emptyList()
        }
    }

    operator fun rangeUntil(other: Point) = (this..other).dropLast(1)

    operator fun plus(other: Point) =
        Point(this.x + other.x, this.y + other.y)
}