package day11

import utils.*
import kotlin.math.pow

private typealias Stone = Long

object solution {
    private val example1 = """
        125 17
    """.trimIndent()

    private const val STONE_MULTIPLIER = 2024

    private fun Long.getNumberOfDigits(): Int {
        var rem = this
        var c = 0
        while (rem > 0L) {
            rem /= 10
            c++
        }
        return c
    }
    private fun Long.hasEvenNumberOfDigits() = getNumberOfDigits() % 2 == 0
    private fun Long.midPoint(): Int = (getNumberOfDigits() / 2)
    private fun Long.split(): Sequence<Stone> = 10.0.pow (midPoint()).toInt().let { divisor -> sequenceOf(this / divisor, this % divisor) }

    private val _cache = mutableMapOf<Stone, Sequence<Stone>>()
    private fun stoneUpdate(stone: Stone): Sequence<Stone> = _cache.getOrPut(stone) {
        when {
            stone == 0L -> sequenceOf(1L)
            stone.hasEvenNumberOfDigits() -> stone.split()
            else -> sequenceOf(stone * STONE_MULTIPLIER)
        }
    }

    private fun solve(input: String): Long {
        return generateSequence(input.split(" ").map { it.toLong() }.asSequence()) { prev ->
            prev.flatMap { stoneUpdate(it) }
        }.take(76).last().fold(0L) { acc, c -> acc.inc() }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve(example1.lines().first()))
        println(solve(getPuzzleInput().first()))
    }
}
