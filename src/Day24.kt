
fun main() {

    val DX = intArrayOf(-1, 0, 1, 0)
    val DY = intArrayOf( 0,-1, 0, 1)

    data class Vector(val x: Double, val y: Double, val z: Double)

    fun intersect2(p1: Vector, d1: Vector, p2: Vector, d2: Vector): Vector? {
        val determinant = d1.x * d2.y - d1.y * d2.x

        if (determinant == 0.0) {
            return null
        }

        val t = ((p2.x - p1.x) * d2.y - (p2.y - p1.y) * d2.x) / determinant
        return Vector(p1.x + t * d1.x, p1.y + t * d1.y, 0.0)
    }

    fun inFuture2(p1: Vector, d1: Vector, i: Vector): Boolean {
        return (i.x - p1.x) * d1.x >= 0 && (i.y - p1.y) * d1.y >= 0
    }

    fun part1(input: List<String>, range: LongRange): Long {
        var ret = 0L

        val points = mutableListOf<Vector>()
        val vectors = mutableListOf<Vector>()
        for (line in input) {
            val splits = line.split("@")
            val (x1, y1, z1) = splits[0].split(",").map { it.toDouble() }
            val (d1, d2, d3) = splits[1].split(",").map { it.toDouble() }

            points.add(Vector(x1, y1, z1))
            vectors.add(Vector(d1, d2, d3))
        }

        for (i in 0 until points.size) {
            for (j in i + 1 until points.size) {
                val intersect = intersect2(points[i], vectors[i], points[j], vectors[j])
                if (intersect != null &&
                    intersect.x >= range.first && intersect.x <= range.last && intersect.y >= range.first && intersect.y <= range.last &&
                    inFuture2(points[i], vectors[i], intersect) &&
                    inFuture2(points[j], vectors[j], intersect)
                    ) {
                    ret++
                    println("Found intersection ${points[i]} / ${points[j]} at ${intersect}")
                }
            }
        }

        return ret
    }


    fun part2(input: List<String>): Long {
        var ret = 0L

        return ret
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day24_test")
    check(part1(testInput, 7L..27L) == 2L)
 //   check(part2(testInput) == 47L)

    val input = readInput("Day24")
    part1(input, 200000000000000L..400000000000000L).println()
    part2(input).println()
}


