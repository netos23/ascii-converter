import 'dart:math';

import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';

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
const whiteSpaceChar = 32;
const newLineChar = 10;

class DiscreteAnimation extends ChangeNotifier {
  final Animation<double> parent;
  bool dirty = false;

  DiscreteAnimation(this.parent) {
    start();
  }

  void start() {
    parent.addListener(() {
      if (parent.value < 0.5) {
        dirty = true;
        return;
      }

      if (parent.value > 0.5 && dirty) {
        dirty = false;
        notifyListeners();
      }
    });
  }
}

class _MatrixRainPainterState {
  final Random _random = Random();

  final List<int> _sizes = [];
  final List<List<int>> _screenChars = [];
  final List<List<int>> _levelsMatrix = [];
  int _maxRows = 10;

  int _maxCols = 10;
  int _buildId = 0;

  _MatrixRainPainterState();

  String getColumn(int column) => String.fromCharCodes(
        _screenChars[column].expand(
          (char) => [char, 10],
        ),
      );

  Iterable<int> getLevelRow(int row) => _levelsMatrix
      .take(_maxCols)
      .map(
        (column) => column[row],
      )
      .map(
        (level) => _buildId - level,
      );

  String getRow(int r) => String.fromCharCodes(
        _screenChars.take(_maxCols).map(
              (column) => column[r],
            ),
      );

  void resize(int columns, int rows) {
    var dirty = false;

    while (columns > _sizes.length) {
      dirty = true;
      _sizes.add(0);
      _screenChars.add([]);
      _levelsMatrix.add([]);
    }
    _maxCols = columns;

    if (!dirty && _maxRows >= rows) {
      return;
    }

    _maxRows = rows;
    _resize(_screenChars, whiteSpaceChar, rows);
    _resize(_levelsMatrix, _buildId, rows);
  }

  void update() {
    _buildId++;
    _addNewChars();
  }

  void _addNewChars() {
    for (var c = 0; c < _sizes.length; c++) {
      if (_sizes[c] > _maxRows || _random.nextDouble() > 0.975) {
        _sizes[c] = 0;
      }

      if (_sizes[c] >= _maxRows) {
        continue;
      }

      _screenChars[c][_sizes[c]] = asciiChars[_nextChar()];
      _levelsMatrix[c][_sizes[c]] = _buildId;
      _sizes[c]++;
    }
  }

  int _nextChar() => _random.nextInt(asciiChars.length);

  void _resize<T>(List<List<T>> matrix, T val, int resizeTo) {
    for (var col in matrix) {
      while (resizeTo > col.length) {
        col.add(val);
      }
    }
  }
}

class MatrixRainStyle {
  final double fontSize;
  final Color textColor;
  final Color backgroundColor;
  final int levels;
  final TextStyle textStyle;
  final Duration duration;

  const MatrixRainStyle({
    required this.fontSize,
    required this.textColor,
    required this.backgroundColor,
    required this.levels,
    required this.textStyle,
    required this.duration,
  });

  const MatrixRainStyle.only(
      {double? fontSize,
      Color? textColor,
      Color? maskColor,
      Color? backgroundColor,
      int? levels,
      TextStyle? textStyle,
      Duration? duration})
      : this(
          fontSize: fontSize ?? 25,
          textColor: textColor ?? const Color(0xff00ff95),
          backgroundColor: backgroundColor ?? Colors.black,
          levels: levels ?? 20,
          textStyle: textStyle ?? const TextStyle(),
          duration: duration ?? const Duration(milliseconds: 123),
        );
}

class _MatrixRainPainter extends CustomPainter {
  final MatrixRainStyle style;
  final _MatrixRainPainterState state;

  const _MatrixRainPainter({
    required this.style,
    required this.state,
  });

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
          style: style.textStyle.copyWith(
            fontSize: style.fontSize,
            foreground: textPaint,
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

  @override
  bool shouldRepaint(_MatrixRainPainter oldDelegate) => true;

  Color _colorLevel(double opacityStep, int level) =>
      style.textColor.withOpacity(
        max(1 - opacityStep * level, 0),
      );
}

class MatrixBackground extends StatefulWidget {
  final Widget? child;

  final MatrixRainStyle style;

  const MatrixBackground({
    Key? key,
    this.child,
    this.style = const MatrixRainStyle.only(),
  }) : super(key: key);

  @override
  State<MatrixBackground> createState() => _MatrixBackgroundState();
}

class _MatrixBackgroundState extends State<MatrixBackground>
    with SingleTickerProviderStateMixin {
  late AnimationController _controller;

  late final _MatrixRainPainterState matrixRainPainterState;

  @override
  Widget build(BuildContext context) {
    return Container(
      width: double.infinity,
      height: double.infinity,
      color: Colors.black,
      child: AnimatedBuilder(
        animation: DiscreteAnimation(_controller),
        child: widget.child,
        builder: (BuildContext context, Widget? child) {
          matrixRainPainterState.update();
          return CustomPaint(
            painter: _MatrixRainPainter(
              style: widget.style,
              state: matrixRainPainterState,
            ),
            child: child,
          );
        },
      ),
    );
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  void initState() {
    super.initState();
    _controller = AnimationController(
      vsync: this,
      duration: widget.style.duration,
    )..repeat();

    matrixRainPainterState = _MatrixRainPainterState();
  }
}
