fun main() {
    fun part1(input: List<String>): Int {
        val split = input.indexOfFirst(String::isEmpty)
        val (freshIngredientIds, ingredientIds) = input.take(split) to input.drop(split + 1)
        val database: List<ClosedRange<Long>> = freshIngredientIds.map { range ->
            val (start, end) = range.split("-").map(String::toLong)
            (start..end)
        }

        return ingredientIds.count {
            val ingredient = it.toLong()
            database.indexOfFirst { range -> ingredient in range } > -1
        }
    }

    fun part2(input: List<String>): Long {
        val split = input.indexOfFirst(String::isEmpty)
        val (freshIngredientIds, _) = input.take(split) to input.drop(split + 1)
        val freshIngredientRanges = freshIngredientIds
            .map { range ->
                val (start, end) = range.split("-").map(String::toLong)
                (start..end)
            }

        // merge the overlapping ranges
        val mergedRanges = mutableListOf<ClosedRange<Long>>()
        freshIngredientRanges
            .sortedBy { it.first }
            .forEach { range ->
                val lastRange = mergedRanges.lastOrNull()
                if (lastRange == null || range.first > lastRange.endInclusive + 1) {
                    mergedRanges.add(range)
                } else {
                    mergedRanges[mergedRanges.lastIndex] =
                        lastRange.start .. maxOf(lastRange.endInclusive, range.last)
                }
            }

        return mergedRanges.sumOf { it.endInclusive - it.start + 1 }
    }

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}