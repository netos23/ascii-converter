package ru.fbtw.ascii_converter.core

import ru.fbtw.ascii_converter.utill.SubMatrix

interface CharMatcher {
    fun prepare(allowedChars: Set<Char>?)
    fun match(segment: SubMatrix<Double>): Char
}