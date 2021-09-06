package components

import kotlinx.css.LinearDimension
import react.*
import react.dom.div
import styled.css
import styled.styledDiv
import styled.styledH1
import styles.CommonStyles
import styles.LogoStyles

class ContentBody : RComponent<RProps, RState>() {

    override fun RBuilder.render() {
        styledDiv {
            css {

            }
            styledH1 {
                css {
                    +LogoStyles.logo
                }
                +"ASCII CONVERTER"
            }
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
            smallPadding {  }
            styledDiv {
                css {
                    +LogoStyles.about
                }
                +"Ascii converter is simple way to create ascii images. "
            }
            smallPadding {  }
            fileUploader {  }
        }
    }

}

fun RBuilder.contentBody(handler: RProps.() -> Unit): ReactElement =
    child(ContentBody::class) {
        attrs.handler()
    }