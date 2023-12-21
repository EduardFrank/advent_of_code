
fun main() {

    val DX = intArrayOf(-1, 0, 1, 0)
    val DY = intArrayOf( 0,-1, 0, 1)

    fun part1(input: List<String>, N: Int): Long {

        data class Node(val x: Int, val y: Int, val d: Int)

        val R = input.size
        val C = input[0].length

        val G = input.map { it.toCharArray() }
        val q = mutableListOf<Node>()
        val V = Array(R) { BooleanArray (C) }
        for (y in 0 until R) for (x in 0 until C) {
            if (input[y][x] == 'S') {
                q.add(Node(x, y, 0))
                break
            }
        }

        while (q.isNotEmpty()) {
            val n = q.removeFirst()

            G[n.y][n.x] = (n.d + '0'.toInt()).toChar();

            if (n.d == N) {
                continue
            }

            if (V[n.y][n.x]) continue

            V[n.y][n.x] = true

            for (i in DX.indices) {
                val x = n.x + DX[i]
                val y = n.y + DY[i]

                if (x < 0 || x >= C || y < 0 || y >= R) continue
                if (V[y][x] || n.d + 1 > N) continue

                if (input[y][x] == '.' || input[y][x] == 'S')
                    q.add(Node(x, y, n.d + 1))
            }
        }

        var ret = 0L
        for (r in 0 until R) {
            for (c in 0 until C) {
                if (G[r][c] == '#' || G[r][c] == '.') continue

                val v = G[r][c] - '1';
                if (v % 2 != 0) G[r][c] = '0'
                else G[r][c] = '.'
            }
        }

        for (r in 0 until R) {
            println(G[r])
            ret += G[r].count { it == '0' }
        }

        return ret
    }

    fun part2(input: List<String>, N: Int): Long {
        /*
        data class Node(val x: Int, val y: Int, val d: Int)

        val IR = input.size
        val IC = input[0].length
        val M = N / (maxOf(IR, IC) / 2) + 2

        val R = M * IR
        val C = M * IC

        fun getChar(x: Int, y: Int): Char {
            val c = input[y % IR][x % IC]
            if (c == 'S') {
                val r = y / IR
                val c = x / IC

                if (r == M / 2 && c == M / 2)
                    return 'S'
                return '.'
            }
            return c
        }

        val G = Array(R) { CharArray(C) }
        for (y in 0 until R) for (x in 0 until C) {
            G[y][x] = getChar(x, y)
        }

        val q = mutableListOf<Node>()
        val V = Array(R) { BooleanArray (C) }
        for (y in 0 until IR) for (x in 0 until IC) {
            if (input[y][x] == 'S') {
                val xx = R / 2 + x
                val yy = C / 2 + y

                q.add(Node(xx, yy, 0))
                break
            }
        }

        while (q.isNotEmpty()) {
            val n = q.removeFirst()

            G[n.y][n.x] = (n.d + '0'.toInt()).toChar();

            if (n.d == N) {
                continue
            }

            if (V[n.y][n.x]) continue

            V[n.y][n.x] = true

            for (i in 0 until DX.size) {
                val x = n.x + DX[i]
                val y = n.y + DY[i]

                if (x < 0 || x >= C || y < 0 || y >= R) continue
                if (V[y][x] || n.d + 1 > N) continue

                if (getChar(x, y) == '.' || getChar(x, y) == 'S')
                    q.add(Node(x, y, n.d + 1))
            }
        }

        var ret = 0L
        for (r in 0 until R) {
            for (c in 0 until C) {
                if (G[r][c] == '#' || G[r][c] == '.') continue

                val v = G[r][c] - '1';
                if (v % 5 != 0) G[r][c] = '0'
                else G[r][c] = '.'
            }
        }

        for (r in 0 until R) {
            println(G[r])
        }

        for (r in 0 until R) {
            ret += G[r].count { it == '0' }
        }

        return ret

         */

        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day21_test")
    check(part1(testInput, 6) == 16L)
//    check(part2(testInput, 10) == 50L)

    val input = readInput("Day21")
    part1(input, 64).println()
    part2(input, 26501365).println()
}


