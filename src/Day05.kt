fun main() {

    fun part1(input: List<String>): Int {
        val maps = mutableMapOf<Long, MutableMap<Long, Long>>()

        fun getMap(i: Long): MutableMap<Long, Long> {
            return maps.getOrDefault(i, mutableMapOf())
        }

        val seeds = getMap(0)
        val nums = input[0].split(":")[1].split(" ").filter { it.isNotEmpty() }.map { it.toLong() }
        for (seed in nums) {
            seeds[seed] = seed
        }

        var i = 1
        var nextMapIdx = 0L
        var fromMap = seeds
        var toMap   = seeds
        while (i < input.size) {
            if (input[i].isEmpty()) {
                i++
                continue
            }

            if (input[i].contains("map")) {
                nextMapIdx++
                fromMap = toMap
                toMap = getMap(nextMapIdx)

                for (entry in fromMap) {
                    toMap.put(entry.value, entry.value)
                }

                i++
                continue
            }
            val digits = input[i].split(" ").map { it.toLong() }
            val to = digits[0]
            val from = digits[1]
            val len = digits[2]

            for (entry in fromMap) {
                if (entry.value >= from && entry.value < from + len) {
                    toMap.remove(entry.value)
                    toMap[entry.key] = to + entry.value - from
                }
            }

            i++
        }

        val locations = toMap.values.sorted()

        return locations[0].toInt()
    }

    fun part2(input: List<String>): Int {
        data class Node(val to: Long, val from: Long, val len: Long) {
            val end: Long = from + len  - 1
        }
        data class Mapping(val f1: Long, val t1: Long, val f2: Long, val t2: Long)

        val seeds = mutableListOf<Mapping>()
        val nums = input[0].split(":")[1].split(" ").filter { it.isNotEmpty() }.map { it.toLong() }
        for (i in 0 until nums.size step 2) {
            seeds.add(Mapping(nums[i], nums[i] + nums[i + 1] - 1, nums[i], nums[i] + nums[i + 1] - 1))
        }

        var i = 1
        var mappings = mutableListOf<Node>()

        while (i < input.size) {
            if (input[i].isEmpty()) {
                i++
                continue
            }

            if (input[i].contains("map")) {
                if (mappings.isEmpty()) {
                    i++
                    continue
                }

                val tmp = mutableListOf<Mapping>()
                for (t in seeds) {
                    tmp.add(t)
                }

                seeds.clear()
                while (tmp.isNotEmpty()) {
                    val t = tmp[tmp.size - 1]
                    tmp.removeAt(tmp.size - 1)

                    var found = false
                    for (m in mappings) {

                        if (t.f2 >= m.from && t.f2 <= m.end && t.t2 >= m.from && t.t2 <= m.end) {
                            val start = t.f2 - m.from + m.to
                            val end = t.t2 - m.from + m.to
                            seeds.add(Mapping(t.f2, t.t2, start, end))
                            found = true
                            break
                        } else if (t.f2 < m.from && t.t2 >= m.from && t.t2 <= m.end) {
                            seeds.add(Mapping(m.from, t.t2, m.to, t.t2 - m.from + m.to))
                            tmp.add(Mapping(t.f2, m.from - 1, t.f2, m.from - 1))
                            found = true
                        } else if (t.f2 >= m.from && t.f2 <= m.end && t.t2 > m.end) {
                            val start = t.f2 - m.from + m.to
                            seeds.add(Mapping(t.f2, m.end, start, start + (m.end - t.f2)))

                            tmp.add(Mapping(m.end + 1, t.t2, m.end + 1, t.t2))

                            found = true
                            break
                        }
                    }

                    if (!found) {
                        seeds.add(t)
                    }
                }

                mappings.clear()

                i++
                continue
            }
            val digits = input[i].split(" ").map { it.toLong() }
            mappings.add(Node(digits[0], digits[1], digits[2]))

            i++
        }

        val tmp = mutableListOf<Mapping>()
        for (t in seeds) {
            tmp.add(t)
        }

        seeds.clear()
        while (tmp.isNotEmpty()) {
            val t = tmp[tmp.size - 1]
            tmp.removeAt(tmp.size - 1)

            var found = false
            for (m in mappings) {

                if (t.f2 >= m.from && t.f2 <= m.end && t.t2 >= m.from && t.t2 <= m.end) {
                    val start = t.f2 - m.from + m.to
                    val end = t.t2 - m.from + m.to
                    seeds.add(Mapping(t.f2, t.t2, start, end))
                    found = true
                    break
                } else if (t.f2 < m.from && t.t2 >= m.from && t.t2 <= m.end) {
                    seeds.add(Mapping(m.from, t.t2, m.to, t.t2 - m.from + m.to))
                    tmp.add(Mapping(t.f2, m.from - 1, t.f2, m.from - 1))
                    found = true
                } else if (t.f2 >= m.from && t.f2 <= m.end && t.t2 > m.end) {
                    val start = t.f2 - m.from + m.to
                    seeds.add(Mapping(t.f2, m.end, start, start + (m.end - t.f2)))

                    tmp.add(Mapping(m.end + 1, t.t2, m.end + 1, t.t2))

                    found = true
                    break
                }
            }

            if (!found) {
                seeds.add(t)
            }
        }

        mappings.clear()

        return seeds.map { it.f2 }.min().toInt()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part2(testInput) == 46)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
