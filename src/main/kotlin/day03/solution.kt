package day03

import org.intellij.lang.annotations.Language
import utils.getPuzzleInput
import kotlin.math.abs

object solution {
    val example1 = """
        xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))
    """.trimIndent()

    @Language("RegExp")
    val regexString = "mul\\((\\d+),(\\d+)\\)".toRegex()
    fun solve(input: List<String>): Int {
        return input.sumOf { line ->
            regexString.findAll(line).sumOf { match -> match.groupValues[1].toInt() * match.groupValues[2].toInt() }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve(example1.split("\n")))
        println(solve(this.getPuzzleInput()))
    }
}
