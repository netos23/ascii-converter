package ru.fbtw.ascii_converter.io

import ru.fbtw.ascii_converter.utill.AnimatedGifEncoder
import ru.fbtw.ascii_converter.utill.Color
import java.awt.Font
import java.awt.font.GlyphVector
import java.awt.image.BufferedImage
import java.io.OutputStream
import java.io.PrintWriter


fun printAsciiImg(asciiImg: Array<Array<Char>>) {
    writeAsciiImgAsText(asciiImg, System.out)
}

fun writeAsciiImgAsImage(
    background: Color = Color(0),
    asciiImg: Array<Array<Pair<Char, Color>>>,
    font: Font
): BufferedImage {
    val dummyImage = BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)
    val fontMetrics = dummyImage.graphics.getFontMetrics(font)


    val vector: GlyphVector = font.createGlyphVector(fontMetrics.fontRenderContext, "@")
    val outline = vector.getOutline(0f, 0f)
    val expectedWidth = outline.bounds.getWidth()
    val expectedHeight = outline.bounds.getHeight()

    val width = (expectedWidth * asciiImg[0].size).toInt()
    val height = (expectedHeight * asciiImg.size).toInt()
    val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)

    val graphics = image.graphics
    graphics.color = java.awt.Color(background.r, background.g, background.b)
    graphics.fillRect(0,0,width, height)
    graphics.font = font
    for ((r, row) in asciiImg.withIndex()) {
        for ((c, ch) in row.withIndex()) {
            graphics.color = java.awt.Color(ch.second.r, ch.second.g, ch.second.b)
            graphics.drawChars(
                charArrayOf(ch.first), 0, 1,
                c * expectedWidth.toInt(), r * expectedHeight.toInt(),
            )
        }
    }

    return image
}

fun writeGifAsciiImage(
    background: Color = Color(0),
    asciiImg: List<Array<Array<Pair<Char, Color>>>>,
    font: Font,
    outputStream: OutputStream
) {

    val animatedGifEncoder = AnimatedGifEncoder()
    animatedGifEncoder.start(outputStream)
    animatedGifEncoder.setDelay(100)
    asciiImg.map { writeAsciiImgAsImage(background, it, font) }
        .forEach(animatedGifEncoder::addFrame)
    animatedGifEncoder.finish()
}

fun writeAsciiImgAsString(
    asciiImg: Array<Array<Char>>,
): List<String> {

    val writer = mutableListOf<String>()
    asciiImg.forEach { chars ->
        val str = StringBuilder()
        chars.forEach { str.append(it) }
        writer.add(str.toString())
    }
    return writer
}

fun writeAsciiImgAsText(
    asciiImg: Array<Array<Char>>,
    outputStream: OutputStream,
) {

    val writer = PrintWriter(outputStream)
    asciiImg.forEach { chars ->
        val str = StringBuilder()
        chars.forEach { str.append(it) }
        writer.println(str)
    }
}


fun writeAsciiImgAsHTML(
    background: Color = Color(0),
    asciiImg: Array<Array<Pair<Char, Color>>>,
    outputStream: OutputStream,
) {
    val head = """
        <html>
            <head>
            
            </head>
            <body style='background: rgb(${background.r},${background.g},${background.b})'>
    """.trimIndent()

    val tail = """
            </body>
        </html>     
    """.trimIndent()

    val writer = PrintWriter(outputStream)
    writer.println(head)

    asciiImg.forEach { chars ->
        val str = StringBuilder()
        chars.forEach {
            val color = it.second
            val ch = it.first
            val computedChar = if (ch == ' ') "&nbsp;" else ch

            str.append("<code style=\'color:rgb(${color.r},${color.g},${color.b})\'>$computedChar</code>")
        }
        writer.println("<div>$str</div>")
    }

    writer.println(tail)
    writer.close()
}