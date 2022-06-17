package components

import react.*
import styled.css
import styled.styledDiv
import styles.LogoStyles

class About() : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +LogoStyles.about
            }
            +"Wake up Neo! ASCII art is a graphic design technique that "
            +"uses computers for presentation and consists of pictures pieced "
            +"together from the 95 printable characters "
            +"defined by the ASCII Standard from 1963 and ASCII compliant "
            +"character sets with proprietary extended characters "
        }
        smallPadding { }
        styledDiv {
            css {
                +LogoStyles.about
            }
            +"Ascii converter is simple way to create ascii images. "
        }
    }
}

fun RBuilder.about(handler: RProps.() -> Unit): ReactElement =
    child(About::class) {
        attrs.handler()
    }