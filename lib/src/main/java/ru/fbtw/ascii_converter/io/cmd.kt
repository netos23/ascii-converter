fun printAsciiImg(asciiImg: Array<Array<Char>>) {
    asciiImg.forEach { chars ->
        val str = StringBuilder()
        chars.forEach { str.append(it) }
        println(str)
    }
}