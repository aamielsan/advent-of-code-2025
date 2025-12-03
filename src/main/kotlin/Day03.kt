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
        val numberOfBattery = 12

        fun maxJoultage(bank: String, battery: String = ""): Long =
            when {
                battery.length == numberOfBattery -> battery.toLong() // Full battery
                else -> {
                    bank
                        .mapIndexed { index, digit ->
                            (digit to bank.substring(index + 1))
                        }
                        .filter { (_, bank) -> bank.length + 1 + battery.length >= numberOfBattery }
                        .maxBy { (digit, _) -> digit }
                        .let { (digit, remainingBank) ->
                            maxJoultage(remainingBank, battery + digit)
                        }
                }
            }

        return input.sumOf(::maxJoultage)
    }

    val input = readInput("Day03")
//    println(part1(input))
    println(part2(input))
}
