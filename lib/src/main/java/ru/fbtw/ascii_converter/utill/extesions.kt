package ru.fbtw.ascii_converter.utill

import java.awt.image.BufferedImage

typealias ImageFilter = (BufferedImage) -> BufferedImage
typealias ColorFilter = (Int) -> Int
typealias RgbFilter = (Color) -> Color
typealias GrayscaleFilter = (Double) -> Double

operator fun BufferedImage.get(r: Int): ImageRow = ImageRow(this, r)

data class ImageRow(val img: BufferedImage, val r: Int) {
    val toArray: Array<Int>
        get() = Array(img.width) { c -> img[r][c] }

    operator fun get(c: Int) = img.getRGB(c, r)
    operator fun set(c: Int, rgb: Int) = img.setRGB(c, r, rgb)
}

fun Double.toPercent() = (this * 100).toInt()
