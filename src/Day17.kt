import java.util.PriorityQueue

fun main() {

    data class Pos(val x: Int, val y: Int, val dx: Int, val dy: Int, val d: Int, val c: Int) {
        var px: Int = 0
        var py: Int = 0

        fun hash() = "$x-$y-$c-$dx-$dy"
    }

    fun solve(G: List<String>, sX: Int, sY: Int, eX: Int, eY: Int, min: Int, max: Int): Int {
        fun ord(y: Int, x: Int): Int = G[y][x] - '0'

        val cache = mutableSetOf<String>()
        val pq = PriorityQueue<Pos> { a, b -> a.d - b.d }
        pq.add(Pos(sX, sY, 1, 0, 0, 1))

        while (pq.isNotEmpty()) {
            val n = pq.poll()

            if (n.c >= min && n.x == eX && n.y == eY)
                return n.d

            if (cache.contains(n.hash()))
                continue

            cache.add(n.hash())

            val tmp = mutableListOf<Pos>()

            if (n.dx != 0) {
                if (n.x + n.dx >= 0 && n.x + n.dx < G[0].length)
                    tmp.add(Pos(n.x + n.dx, n.y, n.dx, n.dy, n.d + ord(n.y, n.x + n.dx), n.c + 1))

                for (k in intArrayOf(-1, +1))
                    if (n.c >= min && n.y + k >= 0 && n.y + k < G.size)
                        tmp.add(Pos(n.x, n.y + k, 0, k, n.d + ord(n.y + k, n.x), 1))

            } else if (n.dy != 0) {
                if (n.y + n.dy >= 0 && n.y + n.dy < G.size)
                    tmp.add(Pos(n.x, n.y + n.dy, n.dx, n.dy, n.d + ord(n.y + n.dy, n.x), n.c + 1))

                for (k in intArrayOf(-1, +1))
                    if (n.c >= min && n.x + k >= 0 && n.x + k < G[0].length)
                        tmp.add(Pos(n.x + k, n.y, k, 0, n.d + ord(n.y, n.x + k), 1))
            }

            val next = tmp.filter {
                it.c <= max && it.x >= 0 && it.x < G[0].length && it.y >= 0 && it.y < G.size
            }

            for (pos in next) {
                if (cache.contains(pos.hash()))
                    continue
                pq.add(pos)
            }
        }

        return Int.MAX_VALUE
    }

    fun part1(G: List<String>): Long {
        return solve(G, 0, 0, G[0].length - 1, G.size - 1, 1, 3).toLong()
    }

    fun part2(G: List<String>): Long {
        return solve(G, 0, 0, G[0].length - 1, G.size - 1, 4, 10).toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day17_test")
    check(part1(testInput) == 102L)
    check(part2(testInput) == 94L)

    val input = readInput("Day17")
    part1(input).println()
    part2(input).println()
}
