import kotlin.math.abs

fun main() {

    data class Command(val dir: Char, val distance : Int)

    val DX = intArrayOf(-1, 0, 1, 0)
    val DY = intArrayOf( 0, 1, 0, -1)

    fun flood(M: Array<CharArray>, r: Int, c: Int, R: Int, C: Int, color: Char) {
        data class Point(val r: Int, val c: Int)

        val V = Array(R) { BooleanArray(C) { false } }

        val q = mutableListOf<Point>()
        q.add(Point(r, c))

        while (q.isNotEmpty()) {
            val p = q.removeAt(q.size - 1)

            if (V[p.r][p.c]) continue

            V[p.r][p.c] = true

            M[p.r][p.c] = color

            for (k in 0 until DX.size) {
                val dr = p.r + DY[k]
                val dc = p.c + DX[k]

                if (dr < 0 || dr >= R || dc < 0 || dc >= C) continue
                if (M[dr][dc] != '.') continue

                q.add(Point(dr, dc))
            }
        }
    }

    fun parse(cmds: List<Command>): Array<CharArray> {
        val M = Array(10000) { CharArray(10000) { '.' } }

        var L = M[0].size / 2
        var T = M.size / 2
        var R = 0
        var C = 0

        var r = M.size / 2
        var c = M[0].size / 2

        M[r][c] = '#'

        for (cmd in cmds) {
            for (k in 0 until cmd.distance) {
                when (cmd.dir) {
                    'R' -> {
                        M[r][c++] = '#'
                    }
                    'D' -> {
                        M[r++][c] = '#'
                    }
                    'L' -> {
                        M[r][c--] = '#'
                    }
                    'U' -> {
                        M[r--][c] = '#'
                    }
                }

                L = minOf(L, c)
                T = minOf(T, r)
                R = maxOf(R, r)
                C = maxOf(C, c)
            }
        }

        val ret = Array(R - T + 1) { CharArray(C - L + 1) {'.'} }
        for (i in T .. R) {
            for (j in L .. C) {
                ret[i - T][j - L] = M[i][j]
            }
        }

        return ret
    }

    fun solve(M: Array<CharArray>): Long {
        val R = M.size
        val C = M[0].size

        for (k in 0 until R) {
            if (M[k][0] == '.') {
                flood(M, k, 0, R, C, 'Y')
            }
            if (M[k][C - 1] == '.')
                flood(M, k, C - 1, R, C, 'Y')
        }

        for (k in 0 until C) {
            if (M[0][k] == '.') {
                flood(M, 0, k, R, C, 'Y')
            }

            if (M[R - 1][k] == '.')
                flood(M, R - 1, k, R, C, 'Y')
        }

        var ret = 0L
        for (r in 0 until R) {
            for (c in 0 until C) {
                if (M[r][c] == 'Y')
                    ret++
            }
        }

        return R * C - ret
    }

    fun part1(input: List<String>): Long {
        return solve(parse(input.map {
            val splits = it.split(" ")
            val t1 = splits[0][0]
            val t2 = splits[1].toInt()

            Command(t1, t2)
        }))
    }

    fun part2(input: List<String>): Long {
        data class Point(val r: Int, val c: Int)

        val points = mutableListOf<Point>()
        points.add(Point(0, 0))

        var b = 0L
        for (line in input) {
            val splits = line.split(" ")
            val hex = splits[2].substring(2, 7)
            val distance = Integer.parseInt(hex, 16)

            b += distance

            var dr = 0
            var dc = 0
            when (splits[2][splits[2].length - 2] - '0') {
                0 -> {
                    dc = 1
                    dr = 0
                }
                1 -> {
                    dc = 0
                    dr = 1
                }
                2 -> {
                    dc = -1
                    dr = 0
                }
                3 -> {
                    dr = -1
                    dc = 0
                }
            }

            val (r, c) = points.last()
            points.add(Point(r + dr * distance, c + dc * distance))
        }

        var ret = 0L

        for (i in 0 until points.size) {
            val t = if (i == 0) points[points.size - 1].c else points[i - 1].c

            ret += (points[i].r.toLong() * (t.toLong() - points[(i + 1) % points.size].c))
        }

        return abs(ret) / 2 - b / 2 + 1 + b
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day18_test")
    check(part1(testInput) == 62L)
    check(part2(testInput) == 952408144115L)

    val input = readInput("Day18")
    part1(input).println()
    part2(input).println()
}
