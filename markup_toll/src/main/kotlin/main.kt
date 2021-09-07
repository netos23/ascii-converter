import ru.fbtw.ascii_converter.utill.*
import java.awt.Font
import java.awt.Label
import java.awt.image.BufferedImage
import java.io.File
import java.nio.charset.Charset
import java.util.*
import javax.imageio.ImageIO
import kotlin.math.pow

fun main(args: Array<String>) {

    for(i in 0..127) {
        val w = 12
        val h = 18
        val bufferedImage = BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY)
        val canvas = bufferedImage.createGraphics()
        canvas.font = Font("Monospaced", Font.BOLD, 20)

        canvas.drawString("${i.toChar()}", 0, 16)
        ImageIO.write(bufferedImage, "png", File("assets/test${i}.png"))

        val matrix = SubMatrix(bufferedImage.toMatrix())
        var acc = 0.0
        for(row in matrix){
            for(col in row){
                val color = Color(col)
                val rr = (color.r / 255.0).pow(2.2)
                val gg = (color.g/ 255.0).pow(2.2)
                val bb = (color.b / 255.0).pow(2.2)

                // Calculate luminance:
                val lum = (0.2126 * rr + 0.7152 * gg + 0.0722 * bb)
                acc += lum
            }
        }

        println("$i - ${i.toChar()} conf = ${(acc / (w * h) * 2).toPercent() % 100}")
    }



}