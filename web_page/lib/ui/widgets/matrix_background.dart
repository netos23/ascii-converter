import 'dart:collection';
import 'dart:math';
import 'package:google_fonts/google_fonts.dart';
import 'dart:ui'
    show
        FontFeature,
        Image,
        ParagraphBuilder,
        ParagraphConstraints,
        ParagraphStyle,
        TextStyle;

import 'package:flutter/material.dart';

const ascii = 'A1B-CDE=FGH2IJ3KLMNO45P6QRSTUV+WXYZ89@';

const whiteSpace = 32;
const asciiChars = [
  65,
  49,
  66,
  45,
  67,
  68,
  69,
  61,
  70,
  71,
  72,
  50,
  73,
  74,
  51,
  75,
  76,
  77,
  78,
  79,
  52,
  53,
  80,
  54,
  81,
  82,
  83,
  84,
  85,
  86,
  43,
  87,
  88,
  89,
  90,
  56,
  57,
  64,
];

class MatrixBackground extends StatefulWidget {
  const MatrixBackground({
    Key? key,
    this.child,
    this.style: const MatrixRainStyle.only(),
  }) : super(key: key);

  final Widget? child;
  final MatrixRainStyle style;

  @override
  State<MatrixBackground> createState() => _MatrixBackgroundState();
}

class _MatrixBackgroundState extends State<MatrixBackground>
    with SingleTickerProviderStateMixin {
  late AnimationController _controller;

  late final MatrixRainPainterState matrixRainPainterState;

  @override
  void initState() {
    super.initState();
    _controller = AnimationController(
      vsync: this,
      duration: const Duration(milliseconds: 150),
    )..repeat();

    matrixRainPainterState = MatrixRainPainterState(widget.style.levels);
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      width: double.infinity,
      height: double.infinity,
      color: Colors.black,
      child: AnimatedBuilder(
        animation: _controller,
        child: widget.child,
        builder: (BuildContext context, Widget? child) {
          matrixRainPainterState.update(_controller.view);

          return CustomPaint(
            painter: MatrixRainPainter(
              style: widget.style,
              state: matrixRainPainterState,
              timestamp: matrixRainPainterState.buildId,
            ),
            child: child,
          );
        },
      ),
    );
  }
}

class MatrixRainPainter extends CustomPainter {
  MatrixRainPainter({
    required this.style,
    required this.state,
    required this.timestamp,
  });

  final MatrixRainStyle style;
  final MatrixRainPainterState state;
  final int timestamp;

  @override
  void paint(Canvas canvas, Size size) {
    final columns = (size.width / (style.fontSize * 0.61)).round();
    final rows = (size.height / style.fontSize).round();
    state.resize(columns, rows);
    final screenRect = Offset.zero & size;

    final gradientStep = 1 / columns;
    final opacityStep = 1 / style.levels;
    for (var r = 0; r < rows; r++) {
      final colors = <Color>[];
      final stops = <double>[];
      final levels = state.getLevelRow(r);

      var currentGradient = 0.0;

      for (var level in levels) {
        var currentColor = _colorLevel(opacityStep, level);

        if (currentGradient > 0.0000001 && currentGradient <= 1) {
          stops.add(currentGradient);
          colors.add(currentColor);
        }

        currentGradient += gradientStep;
        if (currentGradient <= 1) {
          colors.add(currentColor);
          stops.add(currentGradient);
        }
      }

      final textPaint = Paint()
        ..shader = LinearGradient(
          colors: colors,
          stops: stops,
        ).createShader(screenRect);

      final text = TextPainter(
        maxLines: 1,
        text: TextSpan(
          text: state.getRow(r),
          style: GoogleFonts.cutiveMono(
            fontWeight: FontWeight.w400,
            fontSize: style.fontSize,
            foreground: textPaint,
            // color: style.textColor,
          ),
        ),
        textDirection: TextDirection.ltr,
      );

      text.layout(
        maxWidth: size.width,
        minWidth: size.width,
      );
      text.paint(
        canvas,
        Offset(0, style.fontSize * r),
      );
    }
  }

  Color _colorLevel(double opacityStep, int level) =>
      style.textColor.withOpacity(
        max(1 - opacityStep * level, 0),
      );

  @override
  bool shouldRepaint(MatrixRainPainter oldDelegate) =>
      timestamp != oldDelegate.timestamp;
}

class MatrixRainStyle {
  final double fontSize;
  final Color textColor;
  final Color maskColor;
  final Color backgroundColor;
  final int levels;

  const MatrixRainStyle.only({
    double? fontSize,
    Color? textColor,
    Color? maskColor,
    Color? backgroundColor,
    int? levels,
  }) : this(
          fontSize: fontSize ?? 25,
          textColor: textColor ?? const Color(0xff00ff95),
          maskColor: maskColor ?? const Color(0xD000000),
          backgroundColor: backgroundColor ?? Colors.black,
          levels: levels ?? 10,
        );

  const MatrixRainStyle({
    required this.fontSize,
    required this.textColor,
    required this.maskColor,
    required this.backgroundColor,
    required this.levels,
  });
}

class MatrixRainPainterState {
  MatrixRainPainterState(this._maxBrightness);

  final Random random = Random();
  final List<int> _sizes = [];
  final List<List<int>> screenChars = [];
  final List<List<int>> levelsMatrix = [];
  final List<List<int>> colors = [];

  // final LinkedList<CharWithBrightness> screenChars = LinkedList();
  final int _maxBrightness;
  int _maxRows = 10;
  int _maxCols = 10;
  int _buildId = 0;
  bool _dirty = true;

  get buildId => _buildId;

  String getColumn(int column) => String.fromCharCodes(
        screenChars[column].expand(
          (ch) => [ch, 10],
        ),
      );

  String getRow(int r) => String.fromCharCodes(
        screenChars.take(_maxCols).map(
              (column) => column[r],
            ),
      );

  Iterable<int> getLevelRow(int r) => levelsMatrix
      .take(_maxCols)
      .map(
        (c) => c[r],
      )
      .map(
        (l) => _buildId - l,
      );

  void resize(int columns, int rows) {
    var dirty = false;

    while (columns > _sizes.length) {
      dirty = true;
      _sizes.add(0);
      screenChars.add([]);
      levelsMatrix.add([]);
    }
    _maxCols = columns;

    if (!dirty && _maxRows >= rows) {
      return;
    }

    _maxRows = rows;
    _resize(screenChars, whiteSpace, rows);
    _resize(levelsMatrix, _buildId, rows);
    /*for (var col in screenChars) {
      while (rows > col.length) {
        col.add(whiteSpace);
      }
    }*/
  }

  void _resize<T>(List<List<T>> matrix, T val, int resizeTo) {
    for (var col in matrix) {
      while (resizeTo > col.length) {
        col.add(val);
      }
    }
  }

  void update(Animation<double> view) {
    if (view.value <= 0.5) {
      _dirty = true;
    }

    if (view.value > 0.5 && _dirty) {
      _buildId++;

      _dimAndClean();
      _addNewChars();

      _dirty = false;
    }
  }

  void _dimAndClean() {
    /*if (screenChars.isEmpty) {
      return;
    }

    CharWithBrightness? sc = screenChars.first;
    while (sc != null) {
      sc.dim();

      final next = sc.next;
      if (sc.disappear) {
        sc.unlink();
      }
      sc = next;
    }*/
  }

  void _addNewChars() {
    for (var c = 0; c < _sizes.length; c++) {
      if (_sizes[c] > _maxRows || random.nextDouble() > 0.975) {
        _sizes[c] = 0;
      }

      if (_sizes[c] >= _maxRows) {
        continue;
      }

      screenChars[c][_sizes[c]] = asciiChars[random.nextInt(asciiChars.length)];
      levelsMatrix[c][_sizes[c]] = _buildId;
      _sizes[c]++;

      /*screenChars.add(
        CharWithBrightness(
          asciiChars[random.nextInt(asciiChars.length)],
          _maxBrightness,
          c,
          _sizes[c],
        ),
      );*/
    }
  }
}

class CharWithBrightness extends LinkedListEntry<CharWithBrightness> {
  final int char;
  final int x;
  final int y;
  int _brightness;

  CharWithBrightness(this.char, this._brightness, this.x, this.y);

  bool get disappear => _brightness <= 0;

  void dim() => _brightness--;

  bool supportLevel(int level) => _brightness == level;
}
