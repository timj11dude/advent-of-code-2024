package day04

import utils.Matrix
import utils.Vector2D
import utils.getIteratorWithVector
import utils.getPuzzleInput

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

    private const val XMAS = """XMAS"""
    private fun solve(input: List<String>): Int {
        val matrix = Matrix(input.map(String::toCharArray).map(CharArray::toList))
        return Vector2D.allDirections.sumOf { direction ->
            matrix.indicies.count { (x, y) ->
                matrix.getIteratorWithVector(x, y, direction)
                    .asSequence()
                    .take(4)
                    .joinToString("") == XMAS
            }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve(example1.split("\n")))
        println(solve(this.getPuzzleInput()))
    }
}
