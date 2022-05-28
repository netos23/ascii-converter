package ru.fbtw.ascii_converter.core

import java.awt.image.BufferedImage

interface AsciiImgConverter<T> {
    fun convert(img: BufferedImage, config: AsciiImgConverterConfiguration): Array<Array<T>>
}