package day08

import utils.Matrix
import utils.getPuzzleInput
import kotlin.math.abs

private typealias Coords = Pair<Int, Int>
private val Coords.x get() = first
private val Coords.y get() = second
private operator fun Coords.plus(other: Coords) = x + other.x to y + other.y
private operator fun Coords.minus(other: Coords) = x - other.x to y - other.y
private operator fun Coords.times(multiplier: Int) = x * multiplier to y * multiplier

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

    private fun List<Coords>.locateAnteNodes(maxX: Int, maxY: Int): List<Coords> = flatMap { primary ->
        minus(primary).flatMap{ secondary ->
            (secondary - primary).let { delta ->
                generateSequence { delta }
                    .runningFold(primary) { acc, d -> acc + d }
                    .takeWhile { it.x in 0..maxX && it.y in 0..maxY }
            }
        }
    }.filter { it.x in 0..maxX && it.y in 0..maxY}
    /**
     * Given a list of coords, return a count of distinct coords.
     */
    private fun List<Coords>.distinctLocationsCount(): Int = distinct().count()

    private fun solve(input: List<String>): Int {
        val inputList = input.map(String::toList)
        val grid = Matrix(inputList)
        val types = inputList.flatten().distinct().filter { it.isLetterOrDigit() }
        return types.flatMap { type ->
            grid.indices.filter { (x,y) -> grid[x,y] == type }.locateAnteNodes(grid.maxX, grid.maxY)
        }.distinctLocationsCount()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve(example1.lines()))
        println(solve(this.getPuzzleInput()))
    }
}