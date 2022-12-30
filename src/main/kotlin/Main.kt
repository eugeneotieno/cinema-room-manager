const val MENU = """
1. Show the seats
2. Buy a ticket
3. Statistics
0. Exit
"""

fun main() {
    start()
}

fun start(){
    println("Enter the number of rows: ")
    val rows = readln().toInt()

    println("Enter the number of seats in each row:: ")
    val perRow = readln().toInt()

    val seats = seats(rows, perRow)
    val numOfSeats = rows * perRow
    var numOfTickets = 0
    var currentIncome = 0

    var exit: Int = -1
    var reselect = false
    do {
        if (!reselect){
            print(MENU)
            exit = readln().toInt()
            reselect = false
        }
        when(exit){
            1 -> showSeats(seats)
            2 -> {
                println()
                println("Enter a row number: ")
                val selectedRow = readln().toInt()

                println("Enter a seat number in that row: ")
                val seat = readln().toInt()

                try {
                    val selectedSeat = seats[selectedRow][seat]
                    if (selectedSeat.contains("B", ignoreCase = true)){
                        println()
                        println("That ticket has already been purchased!")
                        reselect = true
                    } else {
                        reselect = false
                        seats[selectedRow][seat] = " B"
                        numOfTickets += 1

                        val price: Int = if (numOfSeats < 60){
                            10
                        } else {
                            val frontHalf = (rows / 2)
                            if (selectedRow <= frontHalf){
                                10
                            } else {
                                8
                            }
                        }

                        currentIncome += price
                        println()
                        println("Ticket price: $$price")
                    }
                } catch (e: IndexOutOfBoundsException) {
                    println()
                    println("Wrong input!")
                    reselect = true
                }
            }
            3 -> statistics(rows, perRow, numOfTickets, currentIncome)
        }
    } while (exit != 0)
}

fun seats(rows: Int, perRow: Int): MutableList<MutableList<String>> {
    val list = mutableListOf<MutableList<String>>()

    val firstRow = mutableListOf<String>()
    firstRow.add(" ")
    repeat(perRow) { num ->
        firstRow.add(" ${num + 1}")
    }
    list += firstRow

    repeat(rows) {rowSeat ->
        val row = mutableListOf<String>()
        row.add((rowSeat + 1).toString())
        repeat(perRow) {
            row.add(" S")
        }

        list += row
    }

    return list
}

fun showSeats(seats: MutableList<MutableList<String>>){
    println()
    println("Cinema:")
    for (element in seats){
        for (row in element) {
            print(row)
        }
        println()
    }
}

fun statistics(rows: Int, perRow: Int, numOfTickets: Int, currentIncome: Int){
    var income: Int
    val numOfSeats = rows * perRow
    if (numOfSeats < 60) {
        income = numOfSeats * 10
    } else {
        val frontHalf = (rows / 2)
        income = frontHalf * perRow * 10
        val backHalf = rows - frontHalf
        income += (backHalf * perRow * 8)
    }

    val percentage = numOfTickets.toDouble() / numOfSeats.toDouble() * 100
    val formatPercentage = "%.2f".format(percentage)

    println()
    println("Number of purchased tickets: $numOfTickets")
    println("Percentage: $formatPercentage%")
    println("Current income: $$currentIncome")
    println("Total income: $$income")
}