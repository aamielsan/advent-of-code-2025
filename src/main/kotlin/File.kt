import java.io.File

fun readInput(name: String): List<String> =
    File("src/main/kotlin/$name.txt").readLines()