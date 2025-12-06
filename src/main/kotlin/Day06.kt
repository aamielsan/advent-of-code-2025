fun main() {
    fun part1(input: List<String>): Long {
        val operations = input.takeLast(1).flatMap { it.split(" ").filter(String::isNotEmpty).map(String::trim) }
        var count = 0
        return input
            .dropLast(1)
            .map {
                val arr = it
                    .split(" ")
                    .filter(String::isNotEmpty)
                    .map(String::trim)
                    .map(String::toLong)
                    .withIndex()

                println("arr: ${arr.map { it.value }}")
                count = arr.count()
                arr
            }
            .foldIndexed(MutableList<Long>(count) { 0 }) { idx, acc, line ->
                line.forEach { (index, number) ->
                    println("index: $index, number: $number, $acc")
                    when {
                        idx == 0 -> {
                            acc[index] = number
                        }

                        else -> {
                            acc[index] = when (operations[index]) {
                                "+" -> acc[index] + number
                                "*" -> acc[index] * number
                                else -> error("Unknown operation")
                            }
                        }
                    }
                }
                acc
            }
            .sum()
    }

    fun part2(input: List<String>): Long {
        val splits: List<ClosedRange<Int>> =
            input
                .last()
                .foldIndexed(emptyList<Int>()) { index, acc, s ->
                    when {
                        s == ' ' -> acc
                        else -> acc + index
                    }
                }
                .zipWithNext()
                .map { it.first until it.second - 1 }
                .let {
                    val last = it.last().last + 2 until input.maxBy(String::length).length
                    it + listOf(last)
                }
                .also(::println)

        val operations =
            input
                .takeLast(1)
                .flatMap {
                    it
                        .split(" ")
                        .filter(String::isNotEmpty)
                        .map(String::trim)
                }

        val lines: List<List<String>> =
            input
                .dropLast(1)
                .map { line ->
                    splits.map { range ->
                        line
                            .padEnd(splits.last().endInclusive + 1, ' ')
                            .substring(
                                range.start,
                                range.endInclusive + 1
                            )
                    }
                }

        // transpose, get all numbers in same column
        val transposed =
            (0 until lines.first().size).map { j ->
                (0 until lines.size).map { i ->
                    lines[i][j]
                }
            }

        // for each row, transpose again but for the digits + pad
        val result =
            transposed
                .map { row ->
                    val columns = row.first().length
                    val rows = row.size

                    (0 until columns).map { j ->
                        (0 until rows).fold("") { acc, i ->
                            acc + row[i][j]
                        }
                    }
                }
                // parse to long
                .map { row ->
                    row.map(String::trim).map(String::toLong)
                }
                .also(::println)
                .mapIndexed { index, row ->
                    val operation = operations[index]
                    when (operation) {
                        "+" -> row.sum()
                        "*" -> row.fold(1L) { acc, n -> acc * n }
                        else -> error("Unknown operation")
                    }
                }
                .sum()

        return result
    }

    val input = readInput("Day06")
//    println(part1(input))
    println(part2(input))
}
