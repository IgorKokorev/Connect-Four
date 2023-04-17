fun main() {
    // Write your code here
    val words = readln().split(" ")
    val intList = (words.map { it.toIntOrNull() }).filterNotNull()
    println(intList.maxOfOrNull { it })
}