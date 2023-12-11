fun main() {

    val DX = intArrayOf(-1, 0, 1, 0)
    val DY = intArrayOf( 0,-1, 0, 1)

    data class Point(val r: Int, val c: Int)

    fun shortestPathPart(G: Array<CharArray>, s: Point, e: Point, emptyCols: Set<Int>, emptyRows: Set<Int>, factor: Int): Long {
        val distance = Math.abs(e.r - s.r) + Math.abs(e.c - s.c)

        var diff = 0L
        for (r in Math.min(s.r, e.r) until Math.max(s.r, e.r)) {
            if (emptyRows.contains(r)) {
                diff++
            }
        }

        for (c in Math.min(s.c, e.c) until Math.max(s.c, e.c)) {
            if (emptyCols.contains(c)) {
                diff++
            }
        }

        return distance + (factor - 1) * diff
    }

    fun partHelp(I: List<String>, factor: Int): Long {
        val emptyCols = mutableSetOf<Int>()
        val emptyRows = mutableSetOf<Int>()

        for (r in I.indices) {
            val empty = I[r].count { it == '#' } == 0
            if (empty) {
                emptyRows.add(r)
            }
        }

        for (c in 0 until I[0].length) {
            var count = 0
            for (element in I) {
                if (element[c] == '#')
                    count++
            }
            if (count == 0) {
                emptyCols.add(c)
            }
        }

        val G = Array(I.size) { I[it].toCharArray() }

        val galaxies = mutableListOf<Point>()
        for (r in G.indices) {
            for (c in G[r].indices) {
                if (G[r][c] == '#') {
                    galaxies.add(Point(r, c))
                }
            }
        }

        var ret = 0L
        for (i in 0 until galaxies.size) {
            for (j in i + 1 until galaxies.size) {
                val d = shortestPathPart(G, galaxies[i], galaxies[j], emptyCols, emptyRows, factor)
                ret += d
            }
        }

        return ret
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
