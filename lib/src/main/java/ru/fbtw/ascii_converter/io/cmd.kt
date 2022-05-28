import ru.fbtw.ascii_converter.utill.Color
import java.io.OutputStream
import java.io.PrintWriter

fun printAsciiImg(asciiImg: Array<Array<Char>>) {
    asciiImg.forEach { chars ->
        val str = StringBuilder()
        chars.forEach { str.append(it) }
        println(str)
    }
}


fun printAsciiImgAsHTML(
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
            str.append("<code style=\'color:rgb(${color.r},${color.g},${color.b})\'>${if (ch == ' ') "&nbsp;" else ch}</code>")
        }
        writer.println("<div>$str</div>")
    }

    writer.println(tail)
    writer.close()
}