fun main() {
    fun part1(input: List<String>): Int {
        fun String.maxJoultage(index: Int, tenthsDigit: Char): Int {
            val parts = substring(index + 1)
            if (parts.isEmpty()) return tenthsDigit.digitToInt()
            val onesDigit = parts.max()
            return ("$tenthsDigit$onesDigit").toInt()
        }

        return input.sumOf { bank ->
            bank
                .mapIndexed { index, digit -> bank.maxJoultage(index, digit) }
                .max()
        }
    }

    fun part2(input: List<String>): Long {
        fun maxJoultage(bank: String, battery: String = ""): Long {
            if (battery.length == 12) return battery.toLong()

            val (digit, remainingBank) = bank
                .withIndex()
                .filter { (index, _) -> bank.length - index + battery.length >= 12 }
                .maxBy { (_, digit) -> digit }
                .let { (index, digit) -> digit to bank.substring(index + 1) }

            return maxJoultage(remainingBank, battery + digit)
        }

        return input.sumOf(::maxJoultage)
    }

    val input = readInput("Day03")
//    println(part1(input))
    println(part2(input))
}
