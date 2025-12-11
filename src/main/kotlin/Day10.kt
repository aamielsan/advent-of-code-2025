fun main() {
    /**
     * The manual describes one machine per line. Each line contains a single indicator light diagram in [square brackets], one or more button wiring schematics in (parentheses), and joltage requirements in {curly braces}.
     */
    fun part1(input: List<String>): Int {
        data class S(
            val pattern: String,
            val buttons: List<List<Int>>,
            val joltage: List<Int>,
        )

        fun String.press(button: List<Int>): String =
            this
                .toCharArray()
                .mapIndexed { index, ch ->
                    if (button.contains(index)) {
                        if (ch == '.') '#' else '.'
                    } else {
                        ch
                    }
                }
                .let { String(it.toCharArray()) }

        return input
            .map { line ->
                val parts = line.split(" ")
                val pattern = parts.first().trim('[', ']')
                val buttons = parts.drop(1).takeWhile { it.startsWith('(') && it.endsWith(')') }.map { it.trim('(', ')').split(',').map(String::toInt) }
                val joltage = parts.last().trim('{', '}').split(',').map(String::toInt)
                S(pattern, buttons, joltage)
            }
            .map { s ->
                // Find the less moves to set the indicator lights to match the pattern
                val startState = ".".repeat(s.pattern.length)
                val targetState = s.pattern

                fun toggle(
                    buttons: List<List<Int>>,
                    moves: Int = 0,
                    currentState: String = startState,
                    states: MutableMap<String, Int> = mutableMapOf(startState to 0)
                ): Int {
                    if (currentState == targetState) return moves

                    val nextStates =
                        buttons
                            .map { button -> currentState.press(button) }
                            .filter { state ->
                                // record the least moves to reach this state
                                val recordedMoves = states[state]
                                if (recordedMoves == null || recordedMoves > moves + 1) {
                                    states[state] = moves + 1
                                    true
                                } else {
                                    false
                                }
                            }

                    if (nextStates.isEmpty()) return Int.MAX_VALUE

                    return nextStates.minOf { state ->
                        toggle(buttons, moves + 1, state, states)
                    }
                }

                toggle(s.buttons)
            }
            .sum()
    }


    fun part2(input: List<String>): Long {
        data class S(
            val pattern: String,
            val buttons: List<List<Int>>,
            val joltage: List<Int>,
        )

        //
        return 0L
    }

    val input = readInput("Day10")
    println(part1(input))
//    println(part2(input))
}
