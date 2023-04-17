package connectfour

var rows = 6
var columns = 7
var name1 = ""
var name2 = ""
var is1PlayerTurn = true
var totalGames = 1

const val WIN_MUM = 4

var board: Array<Array<Char>> = arrayOf()

fun main() {
    println("Connect Four")

    getAllData()

    println("$name1 VS $name2")
    println("$rows X $columns board")

    board = Array(rows) { Array(columns) { ' ' } }

    when(game()){
        0 -> println("It is a draw")
        1 -> println("Player ${if (is1PlayerTurn) name2 else name1} won")
    }

    println("Game over!")
}

private fun getAllData() {
    println("First player's name:")
    name1 = readln()
    println("Second player's name:")
    name2 = readln()

    while (true) {
        println(
            "Set the board dimensions (Rows x Columns)\n" +
                    "Press Enter for default (6 x 7)"
        )

        val inp = readln()
        if (inp.isEmpty()) break

        val dimStr = inp.replace(Regex("\\s+"), "").split(Regex("[xX]"))

        if (dimStr.size != 2) {
            println("Invalid input")
            continue
        }

        try {
            rows = dimStr[0].toInt()
            columns = dimStr[1].toInt()
        } catch (e: NumberFormatException) {
            println("Invalid input")
            continue
        }

        if (rows !in 5..9) {
            println("Board rows should be from 5 to 9")
            continue
        }
        if (columns !in 5..9) {
            println("Board columns should be from 5 to 9")
            continue
        }

        break
    }

    println("Do you want to play single or multiple games?\n" +
            "For a single game, input 1 or press Enter\n" +
            "Input a number of games:")

    val inp = readln()
    totalGames = try {
        inp.toInt()
    } catch (e: NumberFormatException) {
        println("Invalid input")
        1
    }
}

// returns -1 if players want to end the game,
// returns 1 if someone won
// returns 0 in case of a draw
private fun game(): Int {
    while (true) {
        printBoard()

        if(checkWin()) return 1
        if(checkDraw()) return 0

        while (true) {

            if (is1PlayerTurn) print(name1)
            else print(name2)
            println("'s turn:")

            val move = readln()
            if (move.equals("end")) return -1

            var c: Int

            try {
                c = move.toInt()
            } catch (e: NumberFormatException) {
                println("Incorrect column number")
                continue
            }

            if (c !in 1..columns) {
                println("The column number is out of range (1 - $columns)")
                continue
            }

            if (makeMove(c - 1)) {
                is1PlayerTurn = !is1PlayerTurn
                break
            }

            println("Column $c is full")
        }
    }
}

fun checkDraw(): Boolean {
    for (c in 0 until columns) {
        if (board[rows-1][c] == ' ') return false
    }
    return true
}

fun checkDirection(fromR: Int, fromC: Int, tillR: Int, tillC: Int, stepR: Int, stepC: Int): Boolean {
    for (r in fromR until  tillR)
        for (c in fromC until  tillC) {
            var isALine = board[r][c] != ' '
            var i = 1
            while (isALine && i < WIN_MUM) {
                if (board[r][c] != board[r + i * stepR][c + i * stepC]) isALine = false
                i++
            }
            if (isALine) return true
        }
    return false
}

fun checkWin(): Boolean {
    if (checkDirection(0, 0, rows - WIN_MUM + 1, columns, 1, 0)) return true
    if (checkDirection(0, 0, rows, columns - WIN_MUM + 1, 0, 1)) return true
    if (checkDirection(0, 0, rows - WIN_MUM + 1, columns - WIN_MUM + 1, 1, 1)) return true
    if (checkDirection(0, WIN_MUM - 1, rows - WIN_MUM + 1, columns, 1, -1)) return true

    return false
}

fun makeMove(c: Int): Boolean {

    if (board[rows-1][c] != ' ') return false

    for (r in 0 until rows) {
        if (board[r][c] == ' ') {
            board[r][c] = if (is1PlayerTurn) 'o' else '*'
            break
        }
    }

    return true
}

fun printBoard() {

    for (i in 1..columns) print(" $i")
    println("")

    for(r in (rows - 1) downTo  0) {
        print("║")
        for(c in 0 until columns) {
            print("${board[r][c]}║")
        }
        println("")
    }

    print("╚═")
    repeat(columns - 1) {
        print("╩═")
    }
    println("╝")
}

