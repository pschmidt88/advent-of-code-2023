import kotlin.math.ceil

fun main() {
    fun part1(input: List<String>): Int {
        val (times, records) = input.map {
            it
                .replace("\\s+".toRegex(), " ")
                .split(" ")
                .drop(1)
        }
        val races = times.zip(records) { time, record -> time.toInt() to record.toInt() }

        return races.map {
            val record = it.second
            val range = 0..it.first
            var wins = 0
            for (i in range) {
               if (i * (it.first-i) > record) {
                   wins++
               }
            }
            wins
        }.reduce(Int::times)
    }

    fun part2(input: List<String>): Int {
        val time = input[0].replace("Time:", "").replace("\\s+".toRegex(), "").toLong()
        val record = input[1].replace("Distance:", "").replace("\\s+".toRegex(), "").toLong()

        val range = 0..time
        var wins = 0
        for (i in range) {
            if (i * (time-i) > record) {
                wins++
            }
        }

        return wins
    }

    check(part1(readInput("Day06_test")) == 288)
    check(part2(readInput("Day06_test")) == 71503)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
