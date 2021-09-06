package ru.fbtw.ascii_converter.core

interface MatchData<P : Comparable<P>> {
    val char: Char
    val predicate: P
}