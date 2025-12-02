data class State(
    val code: Int = 0,
    val position: Int = 50,
)

fun main() {
    fun String.rotation(): Int {
        val max = 100
        val direction = this[0]
        val distance = this.substring(1).toInt()
        return when (direction) {
            'L' -> -distance % max
            else -> distance % max
        }
    }

    fun part1(input: List<String>): Int {
        val state = input.fold(State()) { acc, raw ->
            val rotation = raw.rotation()
            val newPosition = (acc.position + rotation) % 100
            val position = if (newPosition < 0) newPosition + 100 else newPosition
            if (position == 0) {
                acc.copy(
                    code = acc.code + 1,
                    position = position
                )
            } else {
                acc.copy(
                    position = position
                )
            }
        }

        return state.code
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("Day01")
    println(part1(input))
}
