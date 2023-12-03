fun main() {
    fun PartNumber.hasAdjacentSymbol(input: List<String>): Boolean {
        // check same line
        val sameLine = input[lineNumber].let { line ->
            line.substring(searchRange()).any {
                it != '.' && !it.isDigit()
            }
        }

        val previousLine = if (lineNumber - 1 >= 0) {
            input[lineNumber - 1].let { line ->
                line.substring(searchRange()).any {
                    it != '.' && !it.isDigit()
                }
            }
        } else false


        val nextLine = if (lineNumber + 1 < input.size) {
            input[lineNumber + 1].let { line ->
                line.substring(searchRange()).any {
                    it != '.' && !it.isDigit()
                }
            }
        } else false

        return sameLine || previousLine || nextLine
    }

    fun part1(input: List<String>): Int {
       return input.mapIndexed { lineNumber, line ->
            "\\d+".toRegex().findAll(line).map {
                PartNumber(it.value.toInt(), lineNumber, it.range, line)
            }.toList()
        }.flatten().filter {
           it.hasAdjacentSymbol(input)
        }.sumOf { it.value }
    }

    fun part2(input: List<String>): Int {
        val partNumbers = input.mapIndexed { lineNumber, line ->
            "\\d+".toRegex().findAll(line).map {
                PartNumber(it.value.toInt(), lineNumber, it.range, line)
            }.toList()
        }.flatten()

        return input.mapIndexed { lineNumber, line ->
            "\\*".toRegex().findAll(line).map {
                Gear(lineNumber, it.range)
            }.toList()
        }.flatten().sumOf { it.ratio(partNumbers) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

data class Gear(
    val lineNumber: Int,
    val range: IntRange
) {

    private fun searchRange(maxLength: Int): IntRange {
        var start = lineNumber - 1
        if (start < 0) start = 0

        var end = lineNumber + 1
        if (lineNumber == maxLength) end = maxLength

        return start..end
    }

    // because we stored line numbers and positions with the PartNumber, we can just
    // look for intersection with the found position of the gear (*) and position range
    // of the part numbers in surround lines (above, same and below line)
    private fun findAdjacentNumbers(partNumbers: List<PartNumber>): List<PartNumber> {
        val maxLineNumber = partNumbers.maxBy { it.lineNumber }.lineNumber

        val linesToSearch = partNumbers.filter {
            this.searchRange(maxLineNumber).contains(it.lineNumber)
        }

        return linesToSearch.filter {
            it.searchRange().contains(this.range.first)
        }
    }

    fun ratio(partNumbers: List<PartNumber>): Int {
        val adjacentNumbers = findAdjacentNumbers(partNumbers)
        if (adjacentNumbers.size != 2) {
            return 0
        }

        val (first, second) = adjacentNumbers

        return first.value * second.value
    }
}

data class PartNumber(
    val value: Int,
    val lineNumber: Int,
    val range: IntRange,
    val line: String
) {
    fun searchRange(): IntRange {
        var start = range.first - 1
        if (start < 0) {
            start = 0
        }

        var end = range.last + 1
        if (end > line.length - 1) {
            end = line.length - 1
        }

        return start..end
    }
}