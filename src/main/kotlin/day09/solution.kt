package day09

import utils.getPuzzleInputRawAsSequence

object solution {
    private val example1 = """
        2333133121414131402
    """.trimIndent()

    private sealed interface Sector {
        val size: Int
        data class FreeSpace(override val size: Int) : Sector
        data class File(val id: Int, override val size: Int) : Sector
    }
    private val sectorSequence = sequence {
        while (true) {
            yieldAll(listOf(
                Sector.File::class.java,
                Sector.FreeSpace::class.java
            ))
        }
    }

    /**
     * Given an input sequence of integers, produce the sum of all values multiplied by their index
     */
    private fun generateChecksum(input: Iterable<Int>): Long = input.fold(0L to 0L) { (acc, index), fileId -> (acc + (index * fileId)) to index.inc() }.first

    private fun buildDiskMap(input: Sequence<Char>): List<Sector> =
        input.map(Char::digitToInt).withIndex().zip(sectorSequence) { (index, size), type ->
            when (type) {
                Sector.File::class.java -> Sector.File(index / 2, size)
                Sector.FreeSpace::class.java -> Sector.FreeSpace(size)
                else -> throw IllegalArgumentException("unknown type: $type")
            }
        }.toList()

    private fun solve(input: Sequence<Char>): Long {
        val diskMap: List<Sector> = buildDiskMap(input)

        return diskMap.reversed().filterIsInstance<Sector.File>().fold(diskMap) { dm, file ->
            val indexOfFreeSpaceSector =
                dm.indexOfFirst { sector -> sector is Sector.FreeSpace && sector.size >= file.size }
            if (indexOfFreeSpaceSector == -1 || dm.indexOf(file) < indexOfFreeSpaceSector) dm
            else {
                val freeSpaceSector = dm[indexOfFreeSpaceSector]
                dm.take(indexOfFreeSpaceSector) + (if (file.size == freeSpaceSector.size) listOf(file) else listOf(
                    file,
                    Sector.FreeSpace(freeSpaceSector.size - file.size)
                )) + dm.drop(indexOfFreeSpaceSector + 1).map { if (it == file) Sector.FreeSpace(file.size) else it }
            }
        }.flatMap { file ->
            when (file) {
                is Sector.File -> List(file.size) { file.id }
                is Sector.FreeSpace -> List(file.size) { 0 }
            }
        }.let(::generateChecksum)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve(example1.asSequence()))
        println(solve(getPuzzleInputRawAsSequence().filter(Char::isDigit)))
    }
}
