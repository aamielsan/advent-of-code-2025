const val presents = """
###
.#.
###

###
.##
##.

###
##.
#..

###
#.#
#.#

###
###
..#

##.
.##
..#
"""

fun main() {
    fun part1(input: List<String>): Int {
        val sizes: List<Int> = presents
            .split("\n\n")
            .map { it.trim().split("\n") }
            .map { it.joinToString("").count { c -> c == '#'} }

        return input
            .map { line ->
                val (regions, count) = line.split(": ")
                val (cols, rows) = regions.split("x").map(String::toInt)
                val counts = count.trim().split(" ").map(String::toInt)
                (cols * rows) to counts
            }
            .count { (area, counts) ->
                counts.mapIndexed { i, c -> sizes[i] * c }.sum() <= area
            }
    }

    val input = readInput("Day12")
    println(part1(input))
}
