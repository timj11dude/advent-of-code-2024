package utils

fun Any.getPuzzleInput() = this::class.java.getResource("input").readText().split("\n")
fun Any.getPuzzleInputWithoutSplit() = this::class.java.getResource("input").readText()