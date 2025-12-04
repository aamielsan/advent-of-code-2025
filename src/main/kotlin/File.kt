import java.io.File

fun readInput(name: String): MutableList<String> =
    File("src/main/kotlin/$name.txt").readLines().toMutableList()