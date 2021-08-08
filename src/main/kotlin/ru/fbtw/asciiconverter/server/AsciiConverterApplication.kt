package ru.fbtw.asciiconverter.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AsciiConverterApplication

fun main(args: Array<String>) {
    runApplication<AsciiConverterApplication>(*args)
}
