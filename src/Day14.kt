fun main() {
    fun part1(input: List<String>): Int {
        val position = Position(0, 0)
        var steps = 0
        var direction = ""
        var heatLoss = 0
        val last = input.lastIndex to input.last().length

        while (position.x != last.first && position.y != last.second) {
            if (steps == 2) {
                // we need to turn
                // reset steps
                // update position
            }

            // check left, down and right of current position for min value, then go there
            val next = input.nextPosition(position)
            heatLoss += input[next.x][next.y].digitToInt()

            // add to heatloss
            // update position
        }


        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 108)

    val input = readInput("Day14")
    part1(input).println()
    part2(input).println()
}

fun List<String>.nextPosition(current: Position): Position {
    val down = current.copy(x = current.x+1)
    val right = current.copy(y = current.y+1)

    val left =
        if (current.y != 0) {
            current.copy(y = current.y-1)
        } else null

    return listOfNotNull(down, right, left).minBy { this[it.x][it.y] }
}

data class Vehicle(
    val currentPosition: Position,
    private var heatLoss: Int = 0,
    private var steps: Int = 0,
//    private var direction: Direction
) {

}

enum class Direction {
    DOWN, LEFT, RIGHT
}

data class Position(
    var x: Int = 0,
    var y: Int = 0
)