
fun main() {

    fun tilt(maze: Array<CharArray>, dir: Int) {
        if (dir == 0) { // NORTH
            for (i in 0 until maze.size) {
                for (j in 0 until maze[i].size) {
                    if (maze[i][j] == 'O') {
                        var k = i
                        while (k >= 0 && maze[k][j] != '#') {
                            if (k - 1 >= 0 && maze[k - 1][j] == '.') {
                                maze[k - 1][j] = maze[k][j]
                                maze[k][j] = '.'
                            }
                            k--
                        }
                    }
                }
            }
        } else if (dir == 1) { // WEST
            for (i in 0 until maze.size) {
                for (j in 0 until maze[i].size) {
                    if (maze[i][j] == 'O') {
                        var k = j
                        while (k >= 0 && maze[i][k] != '#') {
                            if (k - 1 >= 0 && maze[i][k - 1] == '.') {
                                maze[i][k - 1] = maze[i][k]
                                maze[i][k] = '.'
                            }
                            k--
                        }
                    }
                }
            }
        } else if (dir == 2) { // SOUTH
            for (i in maze.size - 1 downTo 0) {
                for (j in 0 until maze[i].size) {
                    if (maze[i][j] == 'O') {
                        var k = i
                        while (k < maze.size && maze[k][j] != '#') {
                            if (k + 1 < maze.size && maze[k + 1][j] == '.') {
                                maze[k + 1][j] = maze[k][j]
                                maze[k][j] = '.'
                            }
                            k++
                        }
                    }
                }
            }
        } else { // EAST
            for (i in 0 until maze.size) {
                for (j in maze[i].size - 1 downTo 0) {
                    if (maze[i][j] == 'O') {
                        var k = j
                        while (k < maze[i].size && maze[i][k] != '#') {
                            if (k + 1 < maze[i].size && maze[i][k + 1] == '.') {
                                maze[i][k + 1] = maze[i][k]
                                maze[i][k] = '.'
                            }
                            k++
                        }
                    }
                }
            }
        }
    }

    fun part1(input: List<String>): Long {
        var ret = 0L

        val maze = Array(input.size) { CharArray(0) }
        for (i in input.indices) {
            maze[i] =input[i].toCharArray()
        }

        tilt(maze, 0)

        for (i in maze.indices) {
            val c = maze[i].count { it == 'O' }
            ret += (c * (maze.size - i))
        }

        return ret
    }

    fun part2(input: List<String>): Long {
        var ret = 0L

        val maze = Array(input.size) { CharArray(0) }
        for (i in input.indices) {
            maze[i] =input[i].toCharArray()
        }

        val map = mutableMapOf<String, Int>()
        var retIdx = -1
        val cycles = mutableListOf<Array<CharArray>>()
        for (i in 1 .. 10000000) {
            for (dir in intArrayOf(0, 1, 2, 3)) {
                tilt(maze, dir)
            }

            val hash = maze.map { String(it) }.joinToString()
            if (map.contains(hash)) {
                var tmp = 0L
                for (i in maze.indices) {
                    val c = maze[i].count { it == 'O' }
                    tmp += (c * (maze.size - i))
                }

                if (retIdx > 0 && retIdx == i) {
                    break
                }

                if (retIdx == -1) {
                    retIdx = (1000000000 - i) % (map[hash]!! - i) + i
                }
            }

            map[hash] = i

            cycles.add(maze)
        }

        for (i in maze.indices) {
            val c = maze[i].count { it == 'O' }
            ret += (c * (maze.size - i))
        }

        return ret
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 136L)
    check(part2(testInput) == 64L)

    val input = readInput("Day14")
    part1(input).println()
    part2(input).println()
}
