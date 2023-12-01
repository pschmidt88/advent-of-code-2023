fun main() {
    fun part1(input: List<String>): Int {
        return input.map { line ->
            line.filter {
                it.isDigit()
            }
        }.sumOf { "${it.first()}${it.last()}".toInt() }
    }

    fun part2(input: List<String>): Int {
        return part1(
            input.map {
                // replace with number and surround with original string, so it parses eighthree to 83 and not eigh3
                it.replace(
                    "one" to "one1one",
                    "two" to "two2two",
                    "three" to "three3three",
                    "four" to "four4four",
                    "five" to "five5five",
                    "six" to "six6six",
                    "seven" to "seven7seven",
                    "eight" to "eight8eight",
                    "nine" to "nine9nine"
                )
            })
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    val testOutput = part2(testInput)
    check(testOutput == 281) {
        "$testOutput was not right"
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

fun String.replace(vararg pairs: Pair<String, String>): String =
    pairs.fold(this) { s, (old, new) -> s.replace(old, new, ignoreCase = true) }

