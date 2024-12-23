package day19

import utils.getPuzzleInput

object solution {
    private val example1 = """
        r, wr, b, g, bwu, rb, gb, br

        brwrr
        bggr
        gbbr
        rrbgbr
        ubwu
        bwurrg
        brgr
        bbrgwb
    """.trimIndent()

    private val _cache = mutableMapOf<String, Long>()
    private fun solvePattern(towels: List<String>, design: String): Long = when {
        design.isEmpty() -> 1
        else -> _cache.getOrPut(design) {
            towels
                .sumOf { towel ->
                    when (val r = design.removePrefix(towel)) {
                        design -> 0
                        else -> solvePattern(towels, r)
                    }
                }
        }
    }

    private fun solve(input: List<String>): Int {
        val (towels, designs) = input.let { it.first().split(",").map(String::trim) to it.drop(2) }
        return designs.count { design ->
            solvePattern(towels, design) > 0
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve(example1.lines()))
        println(solve(getPuzzleInput()))
    }
}
