package day05

import utils.getPuzzleInput

object solution {
    private val example1 = """
        47|53
        97|13
        97|61
        97|47
        75|29
        61|13
        75|53
        29|13
        97|29
        53|29
        61|53
        97|53
        61|29
        47|13
        75|47
        97|75
        47|61
        75|61
        47|29
        75|13
        53|13

        75,47,61,53,29
        97,61,53,29,13
        75,29,13
        75,97,47,61,53
        61,13,29
        97,13,75,29,47
    """.trimIndent()


    private fun List<String>.parseOrderings(): Map<Int, List<Int>> = map { it.split("|").map(String::toInt) }
        .groupBy({ (f, _) -> f }) { (_, s) -> s }

    private fun List<String>.parseInstructions(): List<List<Int>> = filter(String::isNotBlank)
        .map { it.split(",").map(String::toInt).reversed() }

    private fun solve(input: List<String>): Int {
        val (orderings, updates) = input.partition { it.contains("|") }
            .let { (f, s) -> f.parseOrderings() to s.parseInstructions() }
        return updates.filter { update ->
            update.withIndex().all { (index, instruction) ->
                val ordering = orderings.getOrDefault(instruction, emptyList())
                update.drop(index+1).none { it in ordering }
            }
        }
            .sumOf { it[(it.size / 2)] }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve(example1.split("\n")))
        println(solve(this.getPuzzleInput()))
    }
}