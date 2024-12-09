package day08

import utils.Coordinates
import utils.Matrix
import utils.getPuzzleInput
import utils.CoordinatesUtil.minus
import utils.CoordinatesUtil.plus
import utils.CoordinatesUtil.x
import utils.CoordinatesUtil.y

object solution {
    private val example1 = """
        ............
        ........0...
        .....0......
        .......0....
        ....0.......
        ......A.....
        ............
        ............
        ........A...
        .........A..
        ............
        ............
    """.trimIndent()
    private val example2 = """
        T....#....
        ...T......
        .T....#...
        .........#
        ..#.......
        ..........
        ...#......
        ..........
        ....#.....
        ..........
    """.trimIndent()

    private fun List<Coordinates>.locateAnteNodes(maxX: Int, maxY: Int): List<Coordinates> = flatMap { primary ->
        minus(primary).flatMap { secondary ->
            (secondary - primary).let { delta ->
                generateSequence { delta }
                    .runningFold(primary) { acc, d -> acc + d }
                    .takeWhile { it.x in 0..maxX && it.y in 0..maxY }
            }
        }
    }.filter { it.x in 0..maxX && it.y in 0..maxY }

    /**
     * Given a list of coords, return a count of distinct coords.
     */
    private fun List<Coordinates>.distinctLocationsCount(): Int = distinct().count()

    private fun solve(input: List<String>): Int {
        val inputList = input.map(String::toList)
        val grid = Matrix(inputList)
        val types = inputList.flatten().distinct().filter { it.isLetterOrDigit() }
        return types.flatMap { type ->
            grid.indices.filter { (x, y) -> grid[x, y] == type }.locateAnteNodes(grid.maxX, grid.maxY)
        }.distinctLocationsCount()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve(example1.lines()))
        println(solve(this.getPuzzleInput()))
    }
}