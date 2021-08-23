package ru.fbtw.ascii_converter.core

import java.util.*

data class AsciiImgConverterConfiguration(val w: Int, val h: Int, val allowedChars: SortedSet<Char>? = null)