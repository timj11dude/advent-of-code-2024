package day07

import utils.*

private typealias Operator = (Int,Int) -> Int

object solution {
    private val example1 = """
        190: 10 19
        3267: 81 40 27
        83: 17 5
        156: 15 6
        7290: 6 8 6 15
        161011: 16 10 13
        192: 17 8 14
        21037: 9 7 18 13
        292: 11 6 16 20
    """.trimIndent()

    private val operators = listOf<Operator>(Int::times, Int::plus)
    private fun <T> List<T>.buildPermutations(size: Int): List<List<T>> = when (size) {
        0 -> emptyList()
        1 -> this.map { listOf(it) }
        else -> buildPermutations(size-1).flatMap { ops -> map { ops + it } }
    }

    private fun solve(input: List<String>): Int {
        return input.map { line ->
            line.split(':')
                .let { (result, operands) ->
                    result.toInt() to
                            operands.trim().split(" ").map(String::toInt) }
        }.filter { (result, operands) ->
            val operatorPermutations = operators.buildPermutations(operands.size - 1)
            val testResults = operatorPermutations.flatMap { operations ->
                //todo need to zip iterate through operations for each pair of operands
                operations.map { op ->
                    operands.reduce { f, s -> op(f, s) }
                }
            }
            testResults.any { it == result }
        }.sumOf { it.first }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve(example1.lines()))
//        println(solve(this.getPuzzleInput()))
    }
}