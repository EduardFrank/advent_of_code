fun main() {

    data class Node(val y: Int, val x: Int, val d: Int)

    fun part1Help(node: Node, I: Array<CharArray>): Int {
        var q = mutableListOf<Node>()

        q.add(node)

        var V = Array(I.size) { BooleanArray(I[0].size) }
        fun addNode(y: Int, x: Int, d: Int) {
            if (!V[y][x])
                q.add(Node(y, x, d))
        }

        var ret = 0
        while (q.isNotEmpty()) {
            val n = q.removeAt(0)

            if (V[n.y][n.x]) {
                continue
            }
            V[n.y][n.x] = true

            ret = maxOf(ret, n.d)

            val c = I[n.y][n.x]

            I[n.y][n.x] = 'X'

            when (c) {
                '|' -> {
                    if (n.y - 1 >= 0) {
                        val c2 = I[n.y - 1][n.x]
                        if (c2 == '|' || c2 == '7' || c2 == 'F') {
                            addNode(n.y - 1, n.x, n.d + 1)
                        }
                    }
                    if (n.y + 1 < I.size) {
                        val c2 = I[n.y + 1][n.x]
                        if (c2 == '|' || c2 == 'L' || c2 == 'J') {
                            addNode(n.y + 1, n.x, n.d + 1)
                        }
                    }
                }
                '-' -> {
                    if (n.x + 1 < I[0].size) {
                        val c2 = I[n.y][n.x + 1]
                        if (c2 == '-' || c2 == 'J' || c2 == '7') {
                            addNode(n.y, n.x + 1, n.d + 1)
                        }
                    }
                    if (n.x - 1 >= 0) {
                        val c2 = I[n.y][n.x - 1]
                        if (c2 == '-' || c2 == 'L' || c2 == 'F') {
                            addNode(n.y, n.x - 1, n.d + 1)
                        }
                    }
                }
                'L' -> {
                    if (n.y - 1 >= 0) {
                        val c2 = I[n.y - 1][n.x]
                        if (c2 == 'F' || c2 == '7' || c2 == '|') {
                            addNode(n.y - 1, n.x, n.d + 1)
                        }
                    }

                    if (n.x + 1 < I[0].size) {
                        val c2 = I[n.y][n.x + 1]
                        if (c2 == '-'  || c2 == '7' ||  c2 == 'J') {
                            addNode(n.y, n.x + 1, n.d + 1)
                        }
                    }
                }
                'J' -> {
                    if (n.y - 1 >= 0) {
                        val c2 = I[n.y - 1][n.x]
                        if (c2 == '|' || c2 == 'F' || c2 == '7') {
                            addNode(n.y - 1, n.x, n.d + 1)
                        }
                    }

                    if (n.x - 1 >= 0) {
                        val c2 = I[n.y][n.x - 1]
                        if (c2 == '-' || c2 == 'F' || c2 == 'L') {
                            addNode(n.y, n.x - 1, n.d + 1)
                        }
                    }
                }
                '7' -> {
                    if (n.y + 1 < I.size) {
                        val c2 = I[n.y + 1][n.x]
                        if (c2 == '|' || c2 == 'J' || c2 == 'L') {
                            addNode(n.y + 1, n.x, n.d + 1)
                        }
                    }

                    if (n.x - 1 >= 0) {
                        val c2 = I[n.y][n.x - 1]
                        if (c2 == '-' || c2 == 'F' || c2 == 'L') {
                            addNode(n.y, n.x - 1, n.d + 1)
                        }
                    }
                }
                'F' -> {
                    if (n.y + 1 < I.size) {
                        val c2 = I[n.y + 1][n.x]
                        if (c2 == '|' || c2 == 'J' || c2 == 'L') {
                            addNode(n.y + 1, n.x, n.d + 1)
                        }
                    }

                    if (n.x + 1 < I[0].size) {
                        val c2 = I[n.y][n.x + 1]
                        if (c2 == '-' || c2 == '7' || c2 == 'J') {
                            addNode(n.y, n.x + 1, n.d + 1)
                        }
                    }
                }
            }
        }

/*
        for (i in 0 until I.size) {
            println(I[i].mapIndexed { index, c ->
                if (c == 'X') {
                    "\u001b[31m" + I[i][index] + "\u001b[0m"
                }
                else I[i][index]
            }.joinToString(""))
        }
 */

        return ret
    }

    fun part1(I: List<String>): Int {
        var sY: Int = 0
        var sX: Int = 0
        for (y in 0 until I.size) {
            for (x in 0 until I[y].length) {
                if (I[y][x] == 'S') {
                    sY = y
                    sX = x
                }
            }
        }

        var T = Array(I.size) { CharArray(0) }
        for (i in 0 until I.size)
            T[i] = I[i].toCharArray().copyOf()

        var ret = Int.MAX_VALUE
        for (c in charArrayOf('-', '|', 'J', 'F', '7', 'L')) {
            T[sY][sX] = c
            val tmp = part1Help(Node(sY, sX, 0), T)
            if (tmp == 0) continue
            ret = minOf(ret, tmp)
        }

        return ret
    }

    fun escapes(I: Array<CharArray>, y: Int, x: Int, V: Array<BooleanArray>): Boolean {
        if (y == 0 || x == 0 || y == I.size - 1 || x == I[0].size - 1) {
            return true
        }

        if (I[y][x] == '0') {
            return true
        };
        if (I[y][x] == 'I') {
            return false
        }

        if (V[y][x]) return false
        V[y][x] = true

        val DX = intArrayOf(-1, 0, 1, 0)
        val DY = intArrayOf( 0,-1, 0, 1)

        for (i in 0 until DX.size) {
            val dx = x + DX[i]
            val dy = y + DY[i]

            if (dx < 0 || dy < 0 || dx >= I[0].size || dy >= I.size) {
                continue
            }

            if (I[dy][dx] == 'X') continue

            if (escapes(I, dy, dx, V))
                return true
        }

        return false
    }

    fun escapes(I: Array<CharArray>, y: Int, x: Int): Boolean {
        val V = Array(I.size) { BooleanArray(I[0].size) }
        return escapes(I, y, x, V)
    }

    fun fill(I: Array<CharArray>, y: Int, x: Int, c: Char, V: Array<BooleanArray>) {
        if (V[y][x]) return
        V[y][x] = true
        I[y][x] = c

        val DX = intArrayOf(-1, 0, 1, 0)
        val DY = intArrayOf( 0,-1, 0, 1)

        for (i in 0 until DX.size) {
            val dx = x + DX[i]
            val dy = y + DY[i]

            if (dx < 0 || dy < 0 || dx >= I[0].size || dy >= I.size) {
                continue
            }

            if (I[dy][dx] == 'X') continue

            fill(I, dy, dx, c, V)
        }
    }


    fun fill(I: Array<CharArray>, y: Int, x: Int, c: Char) {
        val V = Array(I.size) { BooleanArray(I[0].size) }
        fill(I, y, x, c, V)
    }

    fun part2(I: List<String>): Int {
        var sY: Int = 0
        var sX: Int = 0
        for (y in 0 until I.size) {
            for (x in 0 until I[y].length) {
                if (I[y][x] == 'S') {
                    sY = y
                    sX = x
                }
            }
        }

        var ret = Int.MIN_VALUE
        for (c in charArrayOf('-', '|', 'J', 'F', '7', 'L')) {
            val T = Array(I.size) { CharArray(0) }
            for (i in 0 until I.size)
                T[i] = I[i].toCharArray().copyOf()

            T[sY][sX] = c
            val tmp = part1Help(Node(sY, sX, 0), T)
            if (tmp == 0) continue

            for (i in 0 until T.size) for (j in 0 until T[i].size) if (T[i][j] != 'X') {
                T[i][j] = '.'
            }

            /*
            for (i in 0 until T.size) {
                println(T[i].mapIndexed { index, c ->
                    if (c == 'X') {
                        "\u001b[31m" + T[i][index] + "\u001b[0m"
                    }
                    else T[i][index]
                }.joinToString(""))
            }
             */

            var T2 = Array(I.size) { CharArray(0) }
            for (i in 0 until I.size) {
                T2[i] = I[i].toCharArray().copyOf()

                for (j in 0 until T[i].size) {
                    if (T[i][j] == '.')
                        T2[i][j] = '.';
                }
            }
            T2[sY][sX] = c

            val T3 = construct(T2)

            for (i in 0 until T3.size) for (j in 0 until T3[i].size) if (T3[i][j] != '.') {
                T3[i][j] = 'X'
            }

            /*
            for (i in 0 until T3.size) {
                println(T3[i].mapIndexed { index, c ->
                    T3[i][index]
                }.joinToString(""))
            }
             */

            for (i in 0 until T.size) {
                for (j in 0 until T[i].size) {
                    if (T[i][j] != 'X' && T[i][j] != '0') {
                        if (escapes(T3, 2 * i, 2 * j)) {
                            fill(T, i, j, '0')
                            fill(T3, 2 * i, 2 * j, '0')
                        } else {
                            fill(T, i, j , 'I')
                            fill(T3, 2 * i, 2 * j, 'I')
                        }

                        /*
                        for (i in 0 until T3.size) {
                            println(T3[i].mapIndexed { index, c ->
                                if (c == 'X') {
                                    "\u001b[31m" + T3[i][index] + "\u001b[0m"
                                }
                                else T3[i][index]
                            }.joinToString(""))
                        }
                        println()
                         */
                    }
                }
            }

            /*
            for (i in 0 until T.size) {
                println(T[i].mapIndexed { index, c ->
                    if (c == 'I') {
                        "\u001b[31m" + T[i][index] + "\u001b[0m"
                    }
                    else T[i][index]
                }.joinToString(""))
            }
             */

            var tmp2 = 0
            for (i in T.indices) for (j in 0 until T[i].size) if (T[i][j] == 'I') tmp2++

            if (tmp2 > 0)
                ret = maxOf(ret, tmp2)
        }

        return ret
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
//    check(part1(testInput) == 8)
//    check(part2(testInput) == 4)

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}

fun construct(I: Array<CharArray>): Array<CharArray> {
    var ret = Array(I.size * 2) { CharArray(0) }

    var h = 0
    for (i in 0 until I.size) {
        ret[h] = CharArray(I[i].size * 2)
        ret[h + 1] = CharArray(I[i].size * 2)

        var k = 0
        for (j in 0 until I[i].size) {
            ret[h][k++] = '.'
            ret[h][k++] = I[i][j]
        }

        for (x in 0 until I[i].size * 2) {
            ret[h + 1][x] = '.'
        }

        h += 2
    }

    for (i in 0 until ret.size) {
        for (j in 0 until ret[i].size) {
            if (ret[i][j] == '.') {
                if (i > 0 && i + 1 < ret.size) {
                    val c1 = ret[i-1][j]
                    val c2 = ret[i+1][j]

                    if (c1 == '|' && (c2 == '|' || c2 == 'J')) {
                        ret[i][j] = '|';
                    } else if (c1 == '7' && (c2 == '|' || c2 == 'J' || c2 == 'L'))  {
                        ret[i][j] = '|';
                    } else if (c2 == '|' && (c2 == '|' ||  c2 == 'J')) {
                        ret[i][j] = '|';
                    } else if (c1 == 'F' && (c2 == '|' || c2 == 'J')) {
                        ret[i][j] = '|';
                    }

                    if (c1 == '|' && (c2 == '|' || c2 == 'J')) {
                        ret[i][j] = '|';
                    } else if (c1 == 'F' && (c2 == '|' || c2 == 'J')) {
                        ret[i][j] = '|'
                    } else if (c1 == '7' && (c2 == '|' ||  c2 == 'J')) {
                        ret[i][j] = '|'
                    }

                    if (c2 == '|' && (c1 == '|' || c1 == 'F')) {
                        ret[i][j] = '|'
                    } else if (c2 == 'L' && (c1 == '|' || c1 == 'F')) {
                        ret[i][j] = '|'
                    }
                }
            }
        }
    }

    for (i in 0 until ret.size) {
        for (j in 0 until ret[i].size) {
            if (ret[i][j] == '.') {
                if (j > 0 && j + 1 < ret[i].size) {
                    val c1 = ret[i][j-1]
                    val c2 = ret[i][j+1]

                    if (c1 == '-' && c2 == '-') {
                        ret[i][j] = '-'
                    } else if (c1 == '-' && c2 == '7') {
                        ret[i][j] = '-';
                    } else if (c1 == 'L' && c2 == '-') {
                        ret[i][j] = '-';
                    } else if (c1 == '-' && c2 == 'J') {
                        ret[i][j] = '-';
                    } else if (c1 == 'F' && (c2 == '-' || c2 == '7' || c2 == 'J')) {
                        ret[i][j] = '-';
                    } else if (c1 == 'L' && (c2 == '7' ||  c2 == 'J')) {
                        ret[i][j] = '-';
                    }
                }
            }
        }
    }

    return ret
}

// 14131 -> too high
// 7066