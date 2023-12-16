fun main() {
    data class Pos(val x: Int, val y: Int, val dx: Int, val dy: Int)

    fun partHelp(G: List<String>, x: Int, y: Int, dx: Int, dy: Int): Long {
        val g = Array(G.size) { CharArray(G[0].length) {'.'} }

        val q = mutableListOf<Pos>()

        q.add(Pos(x, y, dx, dy))
        g[y + dy][x + dx] = '#'

        var count = 0
        while (q.isNotEmpty()) {
            val n = q.removeAt(q.size - 1)

            val nx = n.x + n.dx
            val ny = n.y + n.dy

            if (nx < 0 || nx >= G[0].length || ny < 0 || ny >= G.size) continue

            if (g[ny][nx] == '#') {
                count++
            } else {
                count = 0
            }

            if (count > 10) {
                count = 0
                continue
            }

            g[ny][nx] = '#'

            if (G[ny][nx] == '.') {
                q.add(Pos(nx, ny, n.dx, n.dy))
            } else if (G[ny][nx] == '-') {
                if (n.dx != 0) {
                    q.add(Pos(nx, ny, n.dx, n.dy))
                } else {
                    q.add(Pos(nx, ny, -1, 0))
                    q.add(Pos(nx, ny, +1, 0))
                }
            } else if (G[ny][nx] == '|') {
                if (n.dy != 0) {
                    q.add(Pos(nx, ny, n.dx, n.dy))
                } else {
                    q.add(Pos(nx, ny, 0, -1))
                    q.add(Pos(nx, ny, 0, +1))
                }
            } else if (G[ny][nx] == '/') {
                if (n.dx != 0) {
                    q.add(Pos(nx, ny, 0, -n.dx))
                } else if (n.dy != 0) {
                    q.add(Pos(nx, ny, -n.dy, 0))
                }
            } else if (G[ny][nx] == '\\') {
                if (n.dx != 0) {
                    q.add(Pos(nx, ny, 0, n.dx))
                } else if (n.dy != 0) {
                    q.add(Pos(nx, ny, n.dy, 0))
                }
            }
        }

        var ret = 0L;
        for (l in g) {
            ret += l.count { it == '#' }
        }
        return ret
    }

    fun part1(G: List<String>): Long {
        return partHelp(G, -1, 0, +1, 0)
    }

    fun part2(G: List<String>): Long {
        var ret = 0L

        for (y in G.indices) {
            val tmp1 = partHelp(G, -1, y, +1, 0)
            val tmp2 = partHelp(G, G[0].length, y, -1, 0)

            ret = maxOf(ret, tmp1, tmp2)
        }

        for (x in 0 until G[0].length) {
            val tmp1 = partHelp(G, x, -1, 0, +1)
            val tmp2 = partHelp(G, x, G.size, 0, -1)

            ret = maxOf(ret, tmp1, tmp2)
        }

        return ret
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day16_test")
    check(part1(testInput) == 46L)
    check(part2(testInput) == 51L)

    val input = readInput("Day16")
    part1(input).println()
    part2(input).println()
}
