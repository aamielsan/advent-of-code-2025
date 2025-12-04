data class Point(val x: Int, val y: Int) {
    data class Factory(val size: Int) {
        fun fromIndex(index: Int): Point =
            Point(
                x = index % size,
                y = index / size,
            )

        fun toIndex(point: Point): Int {
            return point.y * size + point.x
        }
    }

    operator fun plus(other: Point): Point =
        Point(
            x = this.x + other.x,
            y = this.y + other.y,
        )
}

// Check all 8 sides
val adjacentPoints = listOf(
    Point(-1, -1),
    Point(-1, 0),
    Point(-1, 1),
    Point(0, -1),
    Point(0, 1),
    Point(1, -1),
    Point(1, 0),
    Point(1, 1),
)

fun main() {
    fun part1(input: List<String>): Int {
        val factory = Point.Factory(input.size) // it's a square grid
        val grid: String = input.joinToString("") // convert to a 1D stuct for easier indexing

        return grid
            .withIndex()
            .count { (index, char) ->
                char == '@' && run {
                    val point = factory.fromIndex(index)
                    val adjacentRolls = adjacentPoints.count { direction ->
                        val adjacentPoint = point + direction
                        adjacentPoint.x in 0 until factory.size &&
                            adjacentPoint.y in 0 until factory.size &&
                            grid[factory.toIndex(adjacentPoint)] == '@'
                    }
                    adjacentRolls < 4
                }
            }
    }

    fun part2(input: List<String>): Int {
        val factory = Point.Factory(input.size) // it's a square grid
        val grid: String = input.joinToString("") // convert to a 1D stuct for easier indexing

        tailrec fun removeRolls(grid: String, count: Int = 0): Int {
            val removableRolls = grid
                .withIndex()
                .filter { (index, char) ->
                    char == '@' && run {
                        val point = factory.fromIndex(index)
                        val adjacentRolls = adjacentPoints.count { direction ->
                            val adjacentPoint = point + direction
                            adjacentPoint.x in 0 until factory.size &&
                                adjacentPoint.y in 0 until factory.size &&
                                grid[factory.toIndex(adjacentPoint)] == '@'
                        }
                        adjacentRolls < 4
                    }
                }

            return when (removableRolls.size) {
                0 -> count
                else -> {
                    val newGrid = grid.toCharArray()
                    removableRolls.forEach { (index, _) ->
                        newGrid[index] = '.'
                    }
                    removeRolls(String(newGrid), count + removableRolls.size)
                }
            }
        }

        return removeRolls(grid)
    }

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
