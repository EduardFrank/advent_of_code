import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {

    data class Point(val r: Int, val c: Int)
    data class Line(val l: Int, val c: Long)

    fun reflectVerticalScore(maze: List<CharArray>): List<Line> {
        var ret = mutableListOf<Line>()
        for (c in 1 until maze[0].size) {
            var reflects = true

            var k = 0
            while (c - 1 - k >= 0 && c + k < maze[0].size) {
                for (r in 0 until maze.size) {
                    if (maze[r][c + k] != maze[r][c - 1 - k]) {
                        reflects = false
                        break
                    }
                }
                if (!reflects) {
                    break
                }
                k++
            }

            if (reflects) {
                ret.add(Line(-c, c.toLong()))
            }
        }

        return ret
    }

    fun reflectHorizontalScore(maze: List<CharArray>): List<Line> {
        var ret = mutableListOf<Line>()

        for (r in 1 until maze.size) {
            var reflects = true

            var k = 0
            while (r - 1 - k >= 0 && r + k < maze.size) {
                for (c in 0 until maze[r].size) {
                    if (maze[r + k][c] != maze[r - 1 - k][c]) {
                        reflects = false
                        break
                    }
                }
                if (!reflects) {
                    break
                }
                k++
            }

            if (reflects) {
                ret.add(Line(r, r * 100L))
            }
        }

        return ret
    }

    fun verticalSmudge(maze: List<CharArray>): List<Point> {
        var ret = emptyList<Point>()

        for (c in 1 until maze[0].size) {
            var diffs = 0
            var diffR = 0
            var diffC = 0
            var diff2C = 0

            var k = 0
            while (c - 1 - k >= 0 && c + k < maze[0].size) {
                for (r in 0 until maze.size) {
                    if (maze[r][c + k] != maze[r][c - 1 - k]) {
                        diffs++

                        diffR = r
                        diffC = c + k

                        diff2C = c - 1 - k
                    }
                }
                k++
            }

            if (diffs == 1) {
                ret = listOf(Point(diffR, diffC), Point(diffR, diff2C))
                break
            }
        }

        return ret
    }

    fun horizontalSmudge(maze: List<CharArray>): List<Point> {
        var ret = emptyList<Point>()

        for (r in 1 until maze.size) {
            var diffs = 0
            var diffR = 0
            var diffC = 0

            var diff2R = 0

            var k = 0
            while (r - 1 - k >= 0 && r + k < maze.size) {
                for (c in 0 until maze[r].size) {
                    if (maze[r + k][c] != maze[r - 1 - k][c]) {
                        diffs++

                        diffR = r - 1 - k
                        diffC = c

                        diff2R = r + k
                    }
                }
                k++
            }
            if (diffs == 1) {
                ret = listOf(Point(diffR, diffC), Point(diff2R, diffC))
                break
            }
        }

        return ret
    }

    fun reflectScore(maze: List<CharArray>): Long {
        val vertical = reflectVerticalScore(maze)
        val horizontal = reflectHorizontalScore(maze)

        return vertical.sumOf { it.c } + horizontal.sumOf { it.c }
    }

    fun reflectScore2(maze: List<CharArray>): Long {
        fun swap(r: Int, c: Int) {
            if (maze[r][c] == '.') {
                maze[r][c] = '#'
            } else {
                maze[r][c] = '.'
            }
        }

        fun extract(L: List<Line>, c: List<Line>): Long {
            if (c.isEmpty()) return 0L
            for (t in c) {
                for (x in L) {
                    if (t.c > 0 && t.l != x.l) {
                        return t.c
                    }
                }
            }
            return 0
        }

        var ret = 0L
        val vertical = reflectVerticalScore(maze)
        if (vertical.isNotEmpty()) {
            var s = horizontalSmudge(maze)

            for (xx in s) {
                swap(xx.r, xx.c)
                var t = reflectHorizontalScore(maze)
                ret = extract(vertical, t)
                if (ret > 0) return ret
                swap(xx.r, xx.c)
            }

            for (xx in s) {
                swap(xx.r, xx.c)
                var t = reflectVerticalScore(maze)
                ret = extract(vertical, t)
                if (ret > 0) return ret
                swap(xx.r, xx.c)
            }

            s = verticalSmudge(maze)

            for (xx in s) {
                swap(xx.r, xx.c)
                var t = reflectHorizontalScore(maze)
                ret = extract(vertical, t)
                if (ret > 0) return ret
                swap(xx.r, xx.c)
            }

            for (xx in s) {
                swap(xx.r, xx.c)
                var t = reflectVerticalScore(maze)
                ret = extract(vertical, t)
                if (ret > 0) return ret
                swap(xx.r, xx.c)
            }
        }

        val horizontal = reflectHorizontalScore(maze)
        if (horizontal.isNotEmpty()) {
            var s = verticalSmudge(maze)

            for (xx in s) {
                swap(xx.r, xx.c)
                var t = reflectVerticalScore(maze)
                ret = extract(horizontal, t)
                if (ret > 0) return ret
                swap(xx.r, xx.c)
            }

            for (xx in s) {
                swap(xx.r, xx.c)
                var t = reflectHorizontalScore(maze)
                ret = extract(horizontal, t)
                if (ret > 0) return ret
                swap(xx.r, xx.c)
            }

            s = horizontalSmudge(maze)
            for (xx in s) {
                swap(xx.r, xx.c)
                var t = reflectVerticalScore(maze)
                ret = extract(horizontal, t)
                if (ret > 0) return ret
                swap(xx.r, xx.c)
            }

            for (xx in s) {
                swap(xx.r, xx.c)
                var t = reflectHorizontalScore(maze)
                ret = extract(horizontal, t)
                if (ret > 0) return ret
                swap(xx.r, xx.c)
            }
        }

        return 0
    }

    fun getMazes(input: List<String>): List<List<String>> {
        val mazes = mutableListOf<List<String>>()

        val maze = mutableListOf<String>()
        for (line in input) {
            if (line.isEmpty()) {
                mazes.add(maze.map { it + "" })
                maze.clear()
            } else {
                maze.add(line)
            }
        }

        mazes.add(maze)

        return mazes;
    }

    fun part1(input: List<String>): Long {
        val mazes = getMazes(input)
        var ret = 0L
        for (m in mazes) {
            ret += reflectScore(m.map { it.toCharArray() })
        }
        return ret
    }

    fun part2(input: List<String>): Long {
        val mazes = getMazes(input)

        var ret = 0L
        for (m in mazes) {
            val maze = m.map { it.toCharArray() }
            ret += reflectScore2(maze)
        }

        return ret
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 405L)
    check(part2(testInput) == 400L)

    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}
