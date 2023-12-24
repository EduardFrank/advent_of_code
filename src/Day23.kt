
fun main() {

    val DX = intArrayOf(-1, 0, 1, 0)
    val DY = intArrayOf( 0,-1, 0, 1)

    fun rec(G: List<CharArray>, sX: Int, sY: Int, d: Int, V: Array<IntArray>, eX: Int, eY: Int, slopes: Set<Int>): Int {
        fun pack(x: Int, y: Int): Int = y * G[0].size + x

        if (V[sY][sX] > 0) return -1

        if (sY == eY && sX == eX) {
            println("1: Found exit with distance: $d")
            return d
        }

        V[sY][sX]++

        var ret = 0
        for (i in DX.indices) {
            val x = sX + DX[i]
            val y = sY + DY[i]

            if (x < 0 || x >= G[0].size || y < 0 || y >= G.size || G[y][x] == '#') continue

            when (G[y][x]) {
                '.' -> {
                    if (!slopes.contains(pack(x, y))) {
                        ret = maxOf(ret, rec(G, x, y, d + 1, V, eX, eY, slopes))
                    }
                }
                '<' -> {
                    if (DX[i] == -1) {
                        ret = maxOf(ret, rec(G, x, y, d + 1, V, eX, eY, slopes + pack(x, y)))
                    }
                }
                '>' -> {
                    if (DX[i] == +1) {
                        ret = maxOf(ret, rec(G, x, y, d + 1, V, eX, eY, slopes + pack(x, y)))
                    }
                }
                'v' -> {
                    if (DY[i] == +1) {
                        ret = maxOf(ret, rec(G, x, y, d + 1, V, eX, eY, slopes + pack(x, y)))
                    }
                }
                '^' -> {
                    if (DY[i] == -1) {
                        ret = maxOf(ret, rec(G, x, y, d + 1, V, eX, eY, slopes + pack(x, y)))
                    }
                }
            }
        }

        V[sY][sX]--

        return ret
    }

    fun part1(input: List<String>): Long {
        val R = input.size
        val C = input[0].length

        val G = input.map { it.toCharArray() }
        val V = Array(R) { IntArray(C) }

        var sX = 0
        var sY = 0
        var eX = 0
        var eY = 0
        for (x in 0 until C) {
            if (G[0][x] == '.') {
                sX = x
                sY = 0
            } else if (G[R - 1][x] == '.') {
                eX = x
                eY = R - 1
            }
        }

        return rec(G, sX, sY, 0, V, eX, eY, emptySet()).toLong()
    }

    fun rec2(G: List<CharArray>, sX: Int, sY: Int, d: Int, V: Array<IntArray>, eX: Int, eY: Int): Int {
        if (V[sY][sX] > 0) return -1

        if (sY == eY && sX == eX) {
            println("1: Found exit with distance: $d")
            return d
        }

        V[sY][sX]++

        var ret = 0
        for (i in DX.indices) {
            val x = sX + DX[i]
            val y = sY + DY[i]

            if (x < 0 || x >= G[0].size || y < 0 || y >= G.size || G[y][x] == '#') continue

            when (G[y][x]) {
                '.', '<', '>', 'v', '^' -> {
                    ret = maxOf(ret, rec2(G, x, y, d + 1, V, eX, eY))
                }
            }
        }

        V[sY][sX]--

        return ret
    }


    fun part2(input: List<String>): Long {
        data class Node(val x: Int, val y: Int, val d: Int)

        val R = input.size
        val C = input[0].length

        fun pack(x: Int, y: Int): Int = y * C + x

        val G = input.map { it.toCharArray() }
        val q = mutableListOf<Node>()
        val V = Array(R) { IntArray(C) }

        var sX = 0
        var sY = 0
        var eX = 0
        var eY = 0
        for (x in 0 until C) {
            if (G[0][x] == '.') {
                sX = x
                sY = 0
            } else if (G[R - 1][x] == '.') {
                eX = x
                eY = R - 1
            }
        }

        return rec2(G, sX, sY, 0, V, eX, eY).toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day23_test")
    check(part1(testInput) == 94L)
    check(part2(testInput) == 154L)

    val input = readInput("Day23")
    part1(input).println()
    part2(input).println()
}


