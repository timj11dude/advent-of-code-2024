package utils

fun Any.getPuzzleInput() = this::class.java.getResource("input").readText().split("\n")
fun Any.getPuzzleInputWithoutSplit() = this::class.java.getResource("input").readText()

/**
 * Get the middle entry of a list
 * @throws IllegalArgumentException if list is even sized
 */
fun <T> List<T>.middle(): T {
    require(this.size % 2 == 1) { "need an odd sized list to find middle: [$this]" }
    return this[this.size / 2]
}