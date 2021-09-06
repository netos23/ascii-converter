package components


import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.events.Event
import react.*
import styled.css
import styled.styledDiv
import styled.styledInput
import styles.FileUploaderStyles

external interface FileUploaderProps : RProps {
    var submitCallback: (Event) -> Unit
}

class FileUploader : RComponent<FileUploaderProps, RState>() {

    override fun RBuilder.render() {
        styledDiv {
            css {
                +FileUploaderStyles.externalBorder
            }
            styledDiv {
                css {
                    +FileUploaderStyles.body
                }
                styledInput {
                    css {
                        +FileUploaderStyles.fileInput
                    }
                    attrs {
                        type = InputType.file
                    }
                }
            }
        }
    }
}

fun RBuilder.fileUploader(handler: FileUploaderProps.() -> Unit): ReactElement =
    child(FileUploader::class) {
        attrs.handler()
    }