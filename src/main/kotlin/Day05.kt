fun main() {
    fun part1(input: List<String>): Int =
        input
            .indexOfFirst(String::isEmpty)
            .let {
                input.take(it).map(String::toRangeInclusiveEnd) to
                    input.drop(it + 1).map(String::toLong)
            }
            .let { (ranges, ids) ->
                ids.count { id -> ranges.any { range -> id in range } }
            }

    fun part2(input: List<String>): Long =
        input
            .takeWhile(String::isNotEmpty)
            .map(String::toRangeInclusiveEnd)
            .mergeOverlappingRanges()
            .sumOf { it.endInclusive - it.start + 1 }

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
