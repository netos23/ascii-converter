package components

import kotlinx.browser.window
import kotlinx.css.rgba
import kotlinx.html.CANVAS
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import react.*
import react.dom.canvas
import kotlin.math.min
import kotlin.random.Random

private val matrix = arrayOf(
    "A", "1", "B", "-", "C", "D", "E", "=", "F", "G", " ",
    "H", "2", "I", "J", " ", "3", "K", "L", "M", "N", " ",
    "O", "4", "5", "P", "6", "Q", "R", " ", "S", "T", "U",
    "V", "+", " ", "W", "X", "Y", "Z", " ", "8", "9", "@"
)
private const val font = 11

@ExperimentalJsExport
@JsExport
external interface MatrixRainProps : RProps {
    var width: Int
    var height: Int
    var isStatic: Boolean
}

@ExperimentalJsExport
@JsExport
class MatrixRainState(val w: Int, val h: Int) : RState {
    var col = 0
    var colVertSizes: Array<Int>

    init {
        col = w / font
        colVertSizes = Array(col) { Random.nextInt(h / font) }
    }

}


@ExperimentalJsExport
@JsExport
class MatrixRain(props: MatrixRainProps) : RComponent<MatrixRainProps, MatrixRainState>(props) {
    private val canvasRef: RReadableRef<CANVAS> = createRef()

    var canvas: HTMLCanvasElement? = null
    lateinit var context: CanvasRenderingContext2D

    private var intervalId: Int? = null

    init {
        state = MatrixRainState(
            w = if (props.isStatic) props.width else window.innerWidth,
            h = if (props.isStatic) props.height else window.innerHeight
        )
    }

    override fun componentDidMount() {
        setCanvas()
        //println("mounted")

        intervalId = window.setInterval({ updateCanvas() }, 123)
        window.addEventListener("resize", { resize() })
    }


    override fun componentDidUpdate(prevProps: MatrixRainProps, prevState: MatrixRainState, snapshot: Any) {
        setCanvas()
        for (i in 0 until min(state.col, prevState.col)) {
            state.colVertSizes[i] = prevState.colVertSizes[i]
        }
        for (i in min(state.col, prevState.col) until state.col) {
            state.colVertSizes[i] = Random.nextInt(state.h / font)
        }
    }

    private fun setCanvas() {
        canvas = canvasRef.current as HTMLCanvasElement?
        context = canvas!!.getContext("2d") as CanvasRenderingContext2D
    }

    private fun resize() {
        if (!props.isStatic) {
            setState(
                MatrixRainState(
                    w = window.innerWidth,
                    h = window.innerHeight
                )
            )
        }
    }

    private fun updateCanvas() {
        // Clear canvas
        context.fillStyle = rgba(0, 0, 0, 0.05)
        context.fillRect(0.0, 0.0, state.w.toDouble(), state.h.toDouble())

        // Setup font for print matrix
        context.fillStyle = "#0F0"
        context.font = "${font}px system-ui"

        // Print matrix
        for (i in 0 until state.col) {
            // Setup char and print
            val ch = matrix.random()
            context.fillText(ch, (i * font).toDouble(), (state.colVertSizes[i] * font).toDouble())

            // Move codepoint
            if (state.colVertSizes[i] * font > state.h && Random.nextDouble(1.0) > 0.975) {
                state.colVertSizes[i] = 0
            }
            state.colVertSizes[i]++
        }

    }

    override fun componentWillUnmount() {
        window.clearInterval(intervalId!!)
    }


    override fun RBuilder.render() {
        val w: Int = if (props.isStatic) props.width else window.innerWidth
        val h: Int = if (props.isStatic) props.height else window.innerHeight

        canvas {
            attrs {
                width = w.toString()
                height = h.toString()
                ref = canvasRef
            }
        }
    }

}

@ExperimentalJsExport
fun RBuilder.matrixRain(handler: MatrixRainProps.() -> Unit): ReactElement =
    child(MatrixRain::class) {
        attrs.handler()
    }