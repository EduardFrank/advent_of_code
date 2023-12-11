import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    data class Point(val r: Int, val c: Int)

    fun shortestPathPart(s: Point, e: Point, emptyCols: Set<Int>, emptyRows: Set<Int>, factor: Int): Long {
        val diff =
            (min(s.r, e.r) ..< max(s.r, e.r)).count(emptyRows::contains) +
            (min(s.c, e.c) ..< max(s.c, e.c)).count(emptyCols::contains)

        return abs(e.r - s.r) + abs(e.c - s.c) +
                (factor - 1) * diff.toLong()
    }

    fun partHelp(I: List<String>, factor: Int): Long {
        val emptyCols = I[0].indices.filter { c -> I.all { it[c] != '#' } }.toSet()
        val emptyRows = I.indices.filter { i -> I[i].none { it == '#' } }.toSet()
        val galaxies = I.flatMapIndexed { r, row ->
            row.mapIndexedNotNull { c, element ->
                if (element == '#') Point(r, c) else null
            }
        }

        return (galaxies.indices).mapIndexed { index, i ->
            (i + 1 ..< galaxies.size).mapIndexed { index, j ->
                shortestPathPart(galaxies[i], galaxies[j], emptyCols, emptyRows, factor)
            }.sum()
        }.sum()
    }

    fun part1(I: List<String>) = partHelp(I, 2)
    fun part2(I: List<String>) = partHelp(I, 1000000)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 374L)
//    check(part2(testInput) == 1030L)

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
