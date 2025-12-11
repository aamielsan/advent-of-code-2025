fun main() {
    // Find all the paths from you to out
    fun part1(input: List<String>): Long {
        val specs = input
            .fold(mapOf<String, Set<String>>()) { acc, line ->
                line
                    .split(":")
                    .let {
                        acc + (it[0] to it[1].trim().split(" ").toSet())
                    }
            }

        fun visit(current: String): Long {
            if (current == "out") return 1L
            return specs[current]!!.sumOf { server ->
                visit(server)
            }
        }

        return visit("you")
    }

    // Find all the paths from svr to out that passes through dac and fft
    fun part2(input: List<String>): Long {
        val specs = input
            .fold(mapOf<String, Set<String>>()) { acc, line ->
                line
                    .split(":")
                    .let {
                        acc + (it[0] to it[1].trim().split(" ").toSet())
                    }
            }

        fun visit(
            current: String,
            needed: Set<String>,
            memo: MutableMap<Pair<String, Set<String>>, Long> = mutableMapOf(),
        ): Long {
            if (current == "out") {
                return if (needed.isEmpty()) 1L else 0L
            }

            val key = current to needed
            if (key in memo) {
                return memo[key]!!
            }

            val servers = specs[current]!!

            memo[key] = servers.sumOf { server ->
                // Remove server from needed if we'll visit it next
                visit(server, needed - server, memo)
            }

            return memo[key]!!
        }

        return visit("svr", setOf("dac", "fft"))
    }

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
