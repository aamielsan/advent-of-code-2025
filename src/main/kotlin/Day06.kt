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

                count = arr.count()
                arr
            }
            .foldIndexed(MutableList<Long>(count) { 0 }) { idx, acc, line ->
                line.forEach { (index, number) ->
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
        val splits: List<IntRange> =
            input
                .last()
                .foldIndexed(emptyList<Int>()) { index, acc, s ->
                    when {
                        s == ' ' -> acc
                        else -> acc + maxOf(0, index)
                    }
                }
                .zipWithNext { a, b -> a until b - 1 }
                .let {
                    it + listOf(
                        it.last().last + 2 until input.maxBy(String::length).length,
                    )
                }

        val operations =
            input
                .last()
                .split(" ")
                .filter(String::isNotEmpty)
                .map(String::trim)

        // transpose, get all numbers in same column
        // for each row, transpose again but for the digits + pad
        val result =
            input
                .dropLast(1)
                .map { line ->
                    val padded = line.padEnd(splits.last().last + 1, ' ')
                    splits.map { range -> padded.substring(range) }
                }
                .transpose()
                .map {
                    // transpose to get the digits
                    it
                        .transposeString()
                        .map(String::trim)
                        .map(String::toLong)
                }
                .withIndex()
                .sumOf { (index, row) ->
                    when (operations[index]) {
                        "+" -> row.sum()
                        "*" -> row.multiply()
                        else -> error("Unknown operation")
                    }
                }

        return result
    }

    val input = readInput("Day06")
//    println(part1(input))
    println(part2(input))
}

fun <T> List<List<T>>.transpose(): List<List<T>> =
    first().indices.map { i ->
        (indices).map { j ->
            this[j][i]
        }
    }

fun List<String>.transposeString(): List<String> =
    first().indices.map { i ->
        (indices)
            .map { j -> this[j][i] }
            .joinToString("")
    }

fun List<Long>.multiply(): Long = fold(1L) { acc, n -> acc * n }