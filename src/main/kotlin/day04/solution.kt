package day04

import utils.*

object solution {
    private val example1 = """MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX
    """.trimIndent()

    val vectorTLBR = Vector2D(-1,1).let { v -> listOf(v, !v)}
    val vectorTRBL = Vector2D(1,1).let { v -> listOf(v, !v)}
    val vectorBLTR = Vector2D(-1,-1).let { v -> listOf(v, !v)}
    val vectorBRTL = Vector2D(1,-1).let { v -> listOf(v, !v)}

    val allVectors = listOf(vectorTLBR, vectorTRBL, vectorBLTR, vectorBRTL)
    val MS = "MS".toList()

    private fun solve(input: List<String>): Int {
        val matrix = Matrix(input.map(String::toCharArray).map(CharArray::toList))
        return matrix.indicies
            .filter { (x,y) -> matrix.getOrNull(x,y) == 'A' }
            .count { (x, y) ->
                allVectors.count { vectors ->
                    matrix.associateView(x,y, vectors).values.toList() == MS
                } == 2
            }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve(example1.split("\n")))
        println(solve(this.getPuzzleInput()))
    }
}
