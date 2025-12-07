fun main() {
    // how many ^ split the laser
    fun part1(input: List<String>): Int {
        val lasersIndex = mutableSetOf(
            input.first().indexOfFirst { it == 'S' }
        )

        val splits = input
            .drop(1)
            .fold(listOf<Int>()) { acc, s ->
                acc + s.indices.filter { s[it] == '^' }
            }
            .count { splitIndex ->
                val isSplitting = lasersIndex.contains(splitIndex)
                if (isSplitting) {
                    // Laser hits a split, so it splits into two lasers
                    lasersIndex.add(splitIndex - 1)
                    lasersIndex.add(splitIndex + 1)

                    // Remove the original laser
                    lasersIndex.remove(splitIndex)
                }
                // Count split
                isSplitting
            }

        return splits
    }

    // how many permutations of paths from S till end of line
    // classic dynamic programming
    fun part2(input: List<String>): Long {
        val startPoint = input.first().indexOfFirst { it == 'S' }
        val endPoint = input.size - 1

        // laser needs to reach the end of the line traversing . and splitting at ^
        fun move(currentLaserIndex: Int, currentLine: Int, memo: MutableMap<Pair<Int, Int>, Long>): Long {
            // end of line; count 1
            if (currentLine == endPoint) return 1L

            // check memo
            val key = Pair(currentLaserIndex, currentLine)
            if (memo.containsKey(key)) return memo[key]!!

            // evaluate character current line and laser index
            val totalPaths = when (input[currentLine][currentLaserIndex]) {
                // splits left and right
                '^' -> move(currentLaserIndex - 1, currentLine + 1, memo) + move(currentLaserIndex + 1, currentLine + 1, memo)

                // just go straight
                '.' -> move(currentLaserIndex, currentLine + 1, memo)

                // shouldn't really happen
                else -> error("unknown character encountered at input[$currentLine][$currentLaserIndex]")
            }

            memo[key] = totalPaths
            return totalPaths
        }

        return move(startPoint, 1, mutableMapOf())
    }

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}