package utils

fun Any.getPuzzleInput() = this::class.java.getResource("input").readText().split("\n")