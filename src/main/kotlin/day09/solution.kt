package day09

import utils.getPuzzleInputRawAsSequence

object solution {
    private val example1 = """
        2333133121414131402
    """.trimIndent()
    private val example2 = """12345"""

    private sealed interface Sector {
        val size: Int
        abstract fun print(): String
        data class FreeSpace(override val size: Int) : Sector {
            override fun print(): String = ".".repeat(size)
        }
        data class File(val id: Int, override val size: Int) : Sector {
            override fun print(): String = id.toString().repeat(size)
        }
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
    private fun generateChecksum(input: Sequence<Int>): Long = input.fold(0L to 0L) { (acc, index), fileId -> (acc + (index * fileId)) to index.inc() }.first

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

        val totalSize = diskMap.sumOf(Sector::size)
        val freeSpace = diskMap.filterIsInstance<Sector.FreeSpace>().sumOf(Sector::size)
        val compressedSize = totalSize - freeSpace

        val reversed: Iterator<Sector.File> = diskMap.reversed().filterIsInstance<Sector.File>().iterator()
        var nextFile: Sector.File = reversed.next()
        fun fillSector(sector: Sector.FreeSpace): List<Sector.File> {
            return buildList {
                var remLen = sector.size
                while (remLen > 0) {
                    val fileSize = nextFile.size
                    if (fileSize > remLen) {
                        add(Sector.File(nextFile.id, remLen))
                        nextFile = Sector.File(nextFile.id, fileSize - remLen)
                        remLen = 0
                    }
                    else {
                        add(nextFile)
                        nextFile = reversed.next()
                        remLen -= fileSize
                    }
                }
            }
        }
        val reversedReMap = diskMap.asSequence().filterIsInstance<Sector.FreeSpace>().map { fillSector(it) }
        return diskMap.asSequence()
            .filterIsInstance<Sector.File>()
            .zip(reversedReMap)
            .flatMap { (diskSector, compressedFiles) -> listOf(diskSector) + compressedFiles }
            .flatMap { file -> List(file.size) { file.id } }.take(compressedSize).let(::generateChecksum)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve(example1.asSequence()))
        println(solve(getPuzzleInputRawAsSequence().filter(Char::isDigit)))
    }
}
