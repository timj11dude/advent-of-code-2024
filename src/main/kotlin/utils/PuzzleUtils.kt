package utils

fun Any.getPuzzleInput() = this::class.java.getResource("input").readText().lines()
fun Any.getPuzzleInputRaw() = this::class.java.getResource("input").readText()
fun Any.getPuzzleInputRawAsSequence(): Sequence<Char> = this::class.java.getResource("input").openStream().bufferedReader().let { bufferedReader ->
    sequence {
        do {
            val n = bufferedReader.read()
            yield(n.toChar())
        } while (n != -1)
    }
}

/**
 * Get the middle entry of a list
 * @throws IllegalArgumentException if list is even sized
 */
fun <T> List<T>.middle(): T {
    require(this.size % 2 == 1) { "need an odd sized list to find middle: [$this]" }
    return this[this.size / 2]
}

/**
 * Similar to runningFold, but output list
 */
fun <T,R> Sequence<T>.runningFoldPaired(seed: R, offset: Boolean = true, function: (R, T) -> R): Sequence<Pair<T,R>> {
    return sequence {
        var accumulator = seed
        if (offset) {
            for (element in this@runningFoldPaired) {
                yield(element to accumulator)
                accumulator = function(accumulator, element)
            }
        } else {
            for (element in this@runningFoldPaired) {
                accumulator = function(accumulator, element)
                yield(element to accumulator)
            }
        }
    }
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
    listOf(1,2,3,4,5).asSequence().runningFoldPaired(0, false) { acc, c -> acc + c }.toList().let { println(it) }
    listOf(1).asSequence().runningFoldPaired(0) { acc, c -> acc + c }.toList().let { println(it) }
}