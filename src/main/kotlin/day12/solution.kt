package day12

import utils.*

/**
 * Collection of all coordinates belonging to a subset area of a Matrix
 */
typealias Region<T> = List<Matrix<T>.LocalCoordinates>
fun <T> Region<T>.getPerimeter(): Int = sumOf { coords -> 4 - (this.filter { other ->  - coords}).size }
fun <T> Region<T>.getArea(): Int = size


object solution {
    private val example1 = """
        AAAA
        BBCD
        BBCC
        EEEC
    """.trimIndent()
    private val example2 = """
        OOOOO
        OXOXO
        OOOOO
        OXOXO
        OOOOO
    """.trimIndent()
    private val example3 = """
        RRRRIICCFF
        RRRRIICCCF
        VVRRRCCFFF
        VVRCCCJFFF
        VVVVCJJCFE
        VVIVCCJJEE
        VVIIICJJEE
        MIIIIIJJEE
        MIIISIJEEE
        MMMISSJEEE
    """.trimIndent()

    private fun solve(input: List<String>): Int {
        val grid = Matrix(input.map(String::toList))
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve(example1.lines()))
        println(solve(example2.lines()))
        println(solve(example3.lines()))
//        println(solve(getPuzzleInput()))
    }
}
