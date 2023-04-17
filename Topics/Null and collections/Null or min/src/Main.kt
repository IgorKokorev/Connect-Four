fun main() {
    // Write your code here
    val words = readln().split(Regex("\\s+"))
    val filtered = words.filter { Regex("[a-l].*").matches(it) }
    val s = filtered.minByOrNull { it.length }
    println(filtered.firstOrNull { it.length == (s?.length ?: -1) })
}