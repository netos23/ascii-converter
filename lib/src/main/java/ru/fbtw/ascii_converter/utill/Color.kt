package ru.fbtw.ascii_converter.utill

data class Color(val r: Int, val g: Int, val b: Int) {
    constructor(color: Int) : this(
        r = color shr 16 and 0xFF,
        g = color shr 8 and 0xFF,
        b = color and 0xFF,
    )
}