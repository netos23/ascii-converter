package components


import kotlinx.html.InputType
import kotlinx.html.hidden
import kotlinx.html.id
import org.w3c.dom.events.Event
import react.*
import react.dom.input
import styled.css
import styled.styledDiv
import styled.styledLabel
import styles.FileUploaderStyles

external interface FileUploaderProps : RProps {
    var submitCallback: (Event) -> Unit
}

class FileUploader : RComponent<FileUploaderProps, RState>() {

    private val fileId = "file-input"

    override fun RBuilder.render() {
        styledDiv {
            css {
                +FileUploaderStyles.externalBorder
            }
            styledDiv {
                css {
                    +FileUploaderStyles.body
                }
                styledLabel {
                    css {
                        +FileUploaderStyles.fileInput
                    }
                    attrs {
                        htmlFor = fileId
                    }
                    +"Browse file..."
                }
                input {
                    attrs {
                        id = fileId
                        type = InputType.file
                        hidden = true
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