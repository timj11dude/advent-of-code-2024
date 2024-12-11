package day11

import utils.*

private typealias Stone = Long

object solution {
    private val example1 = """
        125 17
    """.trimIndent()

    private const val STONE_MULTIPLIER = 2024

    private fun stoneUpdate(stone: Stone): List<Stone> = when {
        stone == 0L -> listOf(1L)
        (stone.toString(10).length % 2) == 0 -> stone.toString(10).let { listOf(it.take(it.length/2).toLong(10), it.drop(it.length/2).toLong(10)) }
        else -> listOf(stone * STONE_MULTIPLIER)
    }

    private fun solve(input: String): Int {
        return generateSequence(input.split(" ").map { it.toLong() }) { prev ->
            prev.flatMap { stoneUpdate(it) }
        }.take(26).last().size
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve(example1.lines().first()))
        println(solve(getPuzzleInput().first()))
    }
}
