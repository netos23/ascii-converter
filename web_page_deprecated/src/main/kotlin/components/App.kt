package components

import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.tr
import styled.css
import styled.styledDiv
import styles.CommonStyles

@ExperimentalJsExport
@JsExport
class App() : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +CommonStyles.contentContainer
            }
            styledDiv {
                css {
                    +CommonStyles.backgroundContainer
                }

                matrixRain {
                    isStatic = false
                }
            }
            styledDiv {
                css {
                    +CommonStyles.foregroundContainer
                }
                contentBody {}
                contentFooter {}
            }
        }

    }
}