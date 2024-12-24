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

    private fun getPatternSolver(towels: List<String>): (String) -> Long {
        val cache = mutableMapOf<String, Long>()
        fun solve(design: String): Long = when {
            design.isEmpty() -> 1
            else -> cache.getOrPut(design) {
                towels
                    .sumOf { towel ->
                        when (val r = design.removePrefix(towel)) {
                            design -> 0
                            else -> solve(r)
                        }
                    }
            }
        }
        return ::solve
    }

    private fun solve(input: List<String>): Long {
        val (towels, designs) = input.let { it.first().split(",").map(String::trim) to it.drop(2) }
        val patternSolver = getPatternSolver(towels)
        return designs.sumOf { design ->
            patternSolver(design)
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve(example1.lines()))
        println(solve(getPuzzleInput()))
    }
}
