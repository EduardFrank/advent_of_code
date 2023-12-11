fun main() {

    val DX = intArrayOf(-1, 0, 1, 0)
    val DY = intArrayOf( 0,-1, 0, 1)

    data class Point(val r: Int, val c: Int)

    fun shortestPathPart(G: Array<CharArray>, s: Point, otherPoints: List<Point>, emptyCols: Set<Int>, emptyRows: Set<Int>, factor: Int, D: MutableList<Long>) {
        val V = Array(G.size) { BooleanArray(G[0].size) }

        data class Node(val v: Point, val d: Long)

        val q = mutableListOf<Node>()
        q.add(Node(s, 0))

        while (q.isNotEmpty()) {
            val n = q.removeAt(0)

            if (V[n.v.r][n.v.c]) continue
            V[n.v.r][n.v.c] = true

            for (p in otherPoints) {
                if (n.v.r == p.r && n.v.c == p.c) {
                    D.add(n.d)
                }
            }

            for (i in 0 until DX.size) {
                val r = n.v.r + DY[i]
                val c = n.v.c + DX[i]

                if (r < 0 || r >= G.size || c < 0 || c >= G[0].size) continue

                q.add(Node(Point(r, c), n.d + 1 + if (emptyCols.contains(c) || emptyRows.contains(r)) factor - 1 else 0))
            }
        }
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
            val otherPoints = galaxies.subList(i + 1, galaxies.size)
            val d = mutableListOf<Long>()

            shortestPathPart(G, galaxies[i], otherPoints, emptyCols, emptyRows, factor, d)

            ret += d.sum()
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
