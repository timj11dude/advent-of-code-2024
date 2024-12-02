package day02

import kotlin.math.abs

object solution {
    val example1 = """
        7 6 4 2 1
        1 2 7 8 9
        9 7 6 2 1
        1 3 2 4 5
        8 6 4 4 1
        1 3 6 7 9
    """.trimIndent()

    fun solve(input: List<String>): Int {
        fun isReportSafe(report: String): Boolean {
            return report.split(" ")
                .map(String::toInt)
                .let { levels ->
                    val isAsc = levels.let { (first, second) -> first < second }
                    levels.zipWithNext().all { (f, s) -> f < s == isAsc && abs(f-s) in 1..3 }
                }
        }
        return input.count(::isReportSafe)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve(example1.split("\n")))
        println(solve(this::class.java.getResource("input").readText().split("\n")))
    }
}