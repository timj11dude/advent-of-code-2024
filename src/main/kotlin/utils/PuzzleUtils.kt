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

/**
 * Produce a unique subset of combinations from this set.
 * @param size
 * @throws IllegalArgumentException if size is larger than this set
 */
fun <T> Set<T>.combinations(size: Int): Set<Set<T>> {
    require(size<=this.size) { "Parameter size [${size}] cannot be larger than this set." }
    fun tailRec(subset: Set<T>, size: Int): Set<Set<T>> {
        return when (size) {
            1 -> subset.map { setOf(it) }.toSet()
            else -> tailRec(subset, size-1).flatMap { ss -> subset.map { s -> ss + s }.toSet() }.toSet()
        }
    }
    return when(size) {
        0 -> emptySet()
        else -> tailRec(this, size).filter { it.size == size }.toSet()
    }
}

fun main() {
    println(setOf('A','B','C').combinations(2))
}