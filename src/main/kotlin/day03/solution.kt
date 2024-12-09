package day03

import org.intellij.lang.annotations.Language
import utils.getPuzzleInputRaw

object solution {
    private val example1 = """
        xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))
    """.trimIndent()

    @Language("RegExp")
    private const val mulRegex = "mul\\((\\d+),(\\d+)\\)"
    private const val doRegex = "do\\(\\)"
    private const val doNotRegex = "don't\\(\\)"
    private val fullRegex = listOf(mulRegex, doRegex, doNotRegex).joinToString("|").toRegex()

    private fun solve(input: String): Int {
        return fullRegex.findAll(input).fold(true to 0) { (doMul, acc), match ->
            when (match.value) {
                "do()" -> (true to acc)
                "don't()" -> (false to acc)
                else -> when (doMul) {
                    true -> (doMul to acc + (match.groupValues[1].toInt() * match.groupValues[2].toInt()))
                    false -> (doMul to acc)
                }
            }
        }.second
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve(example1))
        println(solve(this.getPuzzleInputRaw()))
    }
}
