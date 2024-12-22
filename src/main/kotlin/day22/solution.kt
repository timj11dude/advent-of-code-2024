package day22

import utils.*
import kotlin.math.roundToLong

object solution {
    private val example1 = """
        1
        10
        100
        2024
    """.trimIndent()

    fun pseduoRandom(n: Long): Long {
        fun mix(f: Long, s: Long) = f xor s
        fun prune(f: Long) = f % 16777216
        fun step2(x: Long) = prune(mix(x, (x.toDouble() / 32).toLong() ))
        fun step3(x: Long) = prune(mix(x, x * 2048 ))
        return step3(step2(prune(mix(n, n * 64))))
    }

    private fun solve(input: List<String>): Long {
        return input
            .map(String::toLong)
            .fold(0L) { acc, n ->
                acc + generateSequence(n, ::pseduoRandom)
                .take(2001).last()
            }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve(example1.lines()))
        println(solve(getPuzzleInput()))
    }
}
