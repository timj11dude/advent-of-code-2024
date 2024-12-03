package day02

import utils.getPuzzleInput
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
        fun String.asReport(): Report = split(" ").map(String::toInt)
        fun Report.isSafe(): Boolean {
            fun check(report: Report): Boolean = report
                .let { (first,second) -> first < second }
                .let { isAsc -> report.zipWithNext().all { (f, s) -> f < s == isAsc && abs(f-s) in 1..3 } }
            return if (check(this)) true else indices.any { index ->
                    check(take(index) + drop(index + 1))
                }
        }
        return input.map(String::asReport).count(Report::isSafe)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve(example1.split("\n")))
        println(solve(this.getPuzzleInput()))
    }
}

typealias Level = Int
typealias Report = List<Level>