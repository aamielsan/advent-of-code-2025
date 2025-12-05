fun String.toRangeInclusiveEnd(): ClosedRange<Long> =
    this
        .split("-")
        .map(String::toLong)
        .let { it.first() .. it.last() }

fun String.toRangeExclusiveEnd(): ClosedRange<Long> =
    this
        .split("-")
        .map(String::toLong)
        .let { it.first() until it.last() }

/**
 * Given a list of comparable closed ranges, merges overlapping ranges
 */
fun <T : Comparable<T>> List<ClosedRange<T>>.mergeOverlappingRanges(): List<ClosedRange<T>> =
    this
        .sortedBy { it.start }
        .fold(emptyList<ClosedRange<T>>()) { acc, range ->
            val lastRange = acc.lastOrNull()
            when {
                lastRange == null -> acc + range
                range.start > lastRange.endInclusive -> acc + range
                else -> acc.dropLast(1) + (lastRange.start .. maxOf(lastRange.endInclusive, range.endInclusive))
            }
        }
