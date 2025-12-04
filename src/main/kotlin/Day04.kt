data class PointFactory(val size: Int) {
    fun fromIndex(index: Int): Point {
        return Point(
            x = index % size,
            y = index / size,
        )
    }

    fun toIndex(point: Point): Int {
        return point.y * size + point.x
    }
}

data class Point(val x: Int, val y: Int) {
    companion object
}

// Check all 8 sides
val directions = listOf(
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
    fun part1(input: MutableList<String>): Int {
        val size = input.size
        val factory = PointFactory(size)
        val grid: MutableList<Char> = input.flatMap { it.toMutableList() }.toMutableList()

        // Count the @ that has than less than 4 adjacent @
        var lastCount = 0

        while (true) {
            var count = 0
            val removed = mutableListOf<Int>()
            grid.forEachIndexed { index, char ->
                if (char == '@') {
                    val point = factory.fromIndex(index)
                    var adjacentCount = 0

                    for (dir in directions) {
                        val adjacentPoint = Point(point.x + dir.x, point.y + dir.y)
                        if (adjacentPoint.x in 0 until factory.size && adjacentPoint.y in 0 until factory.size) {
                            val adjacentIndex = factory.toIndex(adjacentPoint)
                            if (grid[adjacentIndex] == '@') {
                                adjacentCount++
                            }
                        }
                    }

                    if (adjacentCount < 4) {
                        removed.add(index)
                        count++
                    }
                }
            }
            lastCount += count
            if (removed.isNotEmpty()) {
                removed.forEach { grid[it] = '.' }
            } else {
                break
            }
        }

        return lastCount
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("Day04")
    println(part1(input))
//    println(part2(input))
}
