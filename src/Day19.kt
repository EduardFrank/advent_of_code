sealed class Action {
    abstract fun accept(value: Int): Boolean
    abstract fun next(): String
}

class SimpleAction(val next: String) : Action() {
    override fun accept(value: Int) = next != "R"
    override fun next() = next
}

class CompareAction(val name: String, val cmp: String, val b: Int, val next: String): Action() {
    override fun accept(value: Int): Boolean {
        if (cmp == "<") return value < b
        else if (cmp == ">") return value > b

        return false
    }

    override fun next() = next
}

fun main() {

    fun part1(input: List<String>): Long {
        data class State(val x: Int, val m: Int, val s: Int, val a: Int) {
            fun getValue(name: String): Int {
                return when (name) {
                    "x" -> x
                    "m" -> m
                    "s" -> s
                    "a" -> a
                    else -> 0
                }
            }
        }

        val workflows = mutableMapOf<String, MutableList<Action>>()
        val states = mutableListOf<State>()

        fun addWorkflow(name: String, rule: Action) {
            val t = workflows.getOrDefault(name, mutableListOf())
            t.add(rule)

            workflows.put(name, t)
        }

        var swap = true
        for (line in input) {
            if (line.isEmpty()) {
                swap = false
                continue
            }

            if (swap) {
                val splits = line.split("{")
                val wf = splits[0]

                val tt = splits[1].replace("}", "").split(",")
                for (t in tt) {
                    if (t.contains("<") || t.contains(">")) {
                        val uuu = t.split(":")
                        val name = "${uuu[0][0]}"
                        val cmp = "${uuu[0][1]}"
                        val value = uuu[0].substring(2).toInt()

                        addWorkflow(wf, CompareAction(name, cmp, value, uuu[1]))
                    } else {
                        addWorkflow(wf, SimpleAction(t))
                    }
                }
            } else {
                val splits = line.replace("}", "").replace("{", "").split(",")
                var x = 0
                var m = 0
                var s = 0
                var a = 0
                for (t in splits) {
                    val tt = t.split("=")
                    val name = tt[0]
                    val value = tt[1].toInt()

                    when (name) {
                        "x" -> x = value
                        "m" -> m = value
                        "s" -> s = value
                        "a" -> a = value
                    }
                }

                states.add(State(x, m, s, a))
            }
        }

        fun apply(name: String, state: State): Int {
            if (name == "A") {
                return state.x + state.s + state.a + state.m
            } else if (name == "R") {
                return 0
            }

            val actions = workflows.getOrDefault(name, mutableListOf())
            if (actions.isEmpty()) return 0

            for (action in actions) {
                when (action) {
                    is CompareAction -> {
                        if (action.accept(state.getValue(action.name))) {
                            return apply(action.next, state)
                        }
                    }
                    is SimpleAction -> {
                        if(action.accept(42)) {
                            return apply(action.next, state)
                        }
                    }
                }
            }

            return 0
        }


        var ret = 0L
        for (state in states) {
            ret += apply("in", state)
        }

        return ret
    }

    fun part2(input: List<String>): Long {
        data class State(val ranges: Map<String, IntRange>) {
            fun getRange(name: String) = ranges[name]!!

            fun copy(name: String, range: IntRange): State {
                val tmp = ranges.toMutableMap()
                tmp[name] = range
                return State(tmp)
            }

            override fun toString(): String {
                val ret = StringBuilder()
                for (entry in ranges.entries) {
                    ret.append("${entry.key} [${entry.value}] ")
                }
                ret.append(" ${score()}")

                return ret.toString()
            }

            fun score(): Long {
                var ret = 1L
                for (entry in ranges.entries) {
                    ret *= (entry.value.last - entry.value.first + 1)
                }
                return ret
            }
        }

        val workflows = mutableMapOf<String, MutableList<Action>>()

        fun addWorkflow(name: String, rule: Action) {
            val t = workflows.getOrDefault(name, mutableListOf())
            t.add(rule)

            workflows[name] = t
        }

        for (line in input) {
            if (line.isEmpty()) {
                break
            }

            val splits = line.split("{")
            val wf = splits[0]

            val tt = splits[1].replace("}", "").split(",")
            for (t in tt) {
                if (t.contains("<") || t.contains(">")) {
                    val uuu = t.split(":")
                    val name = "${uuu[0][0]}"
                    val cmp = "${uuu[0][1]}"
                    val value = uuu[0].substring(2).toInt()

                    addWorkflow(wf, CompareAction(name, cmp, value, uuu[1]))
                } else {
                    addWorkflow(wf, SimpleAction(t))
                }
            }
        }

        val names = "xmas"

        fun apply(name: String, s: State): Long {
            var state = s

            if (name == "A") {
                val ret = s.score()
//                println(s.toString())
                return ret
            } else if (name == "R") {
                return 0
            }

            val actions = workflows.getOrDefault(name, mutableListOf())
            if (actions.isEmpty()) return 0

            var ret = 0L
            for (action in actions) {
                when (action) {
                    is CompareAction -> {

                        for (element in names) {
                            val name = "$element"
                            if (action.name == name) {
                                val range = state.getRange(name)

                                if (action.cmp == "<") {

                                    if (action.b - 1 in range) {
                                        ret += apply(action.next,
                                            state.copy(name, IntRange(range.first, minOf(action.b - 1, range.last)))
                                        )
                                    }

                                    state = state.copy(name, IntRange(maxOf(action.b, range.first), range.last))
                                } else if (action.cmp == ">") {
                                    if (action.b + 1 in range) {
                                        ret += apply(action.next,
                                            state.copy(name, IntRange(maxOf(action.b + 1, range.first), range.last))
                                        )
                                    }

                                    state = state.copy(name, IntRange(range.first, minOf(range.last, action.b)))
                                }

                                break
                            }
                        }
                    }
                    is SimpleAction -> {
                        if(action.accept(42)) {
                            ret += apply(action.next, state)
                        }
                    }
                }
            }

            return ret
        }

        return apply("in", State(names.map { it.toString() to 1..4000 }.toMap()))
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day19_test")
    check(part1(testInput) == 19114L)
    check(part2(testInput) == 167409079868000L)

    val input = readInput("Day19")
    part1(input).println()
    part2(input).println()
}
