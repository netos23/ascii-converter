package styles

import kotlinx.css.*
import styled.StyleSheet

object CommonStyles : StyleSheet("common-styles", true) {
    val smallPadding by css {
        margin = "2vh"
    }

    val contentContainer by css {
        position = Position.relative
    }

    val backgroundContainer by css {
        position = Position.absolute
        zIndex = -1
    }


    val foregroundContainer by css {
        position = Position.absolute
        margin(
            top = LinearDimension("5vh"),
            left = LinearDimension("10vw"),
            right = LinearDimension("10vw"),
        )
        padding(
            vertical = LinearDimension("2vh"),
            horizontal = LinearDimension("2vw")
        )

        backgroundColor = rgba(0, 0, 0, 0.6)
    }
}