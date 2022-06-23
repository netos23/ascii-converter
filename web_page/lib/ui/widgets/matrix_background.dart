import 'dart:collection';
import 'dart:math';
import 'dart:ui'
    show
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
    this.style: const MatrixRainStyle._(),
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
    final screenRect = Offset.zero & size;
    // canvas.drawRect(screenRect, Paint()..color = backgroundColor);

    var maskPaint = Paint()..color = style.maskColor;
    var textPaint = Paint()..color = style.textColor;

    canvas.drawRect(screenRect, maskPaint);

    final columns = (size.width / style.fontSize).round();
    final rows = (size.height / style.fontSize).round();
    state.resize(columns, rows);

    for (var b = 1; b <= style.levels; b++) {
      for (var char in state.getLevel(b)) {
        final text = TextPainter(
          text: TextSpan(
            text: String.fromCharCode(char.char),
            style: TextStyle(
              fontSize: style.fontSize,
              foreground: textPaint,
              // color: style.textColor,
            ),
          ),
          textDirection: TextDirection.ltr,
        );
        text.layout(maxWidth: style.fontSize, minWidth: style.fontSize);
        text.paint(
          canvas,
          Offset(
            style.fontSize * char.x,
            style.fontSize * char.y,
          ),
        );
      }
    }
  }

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

  const MatrixRainStyle._()
      : this(
          fontSize: 11,
          textColor: const Color(0xff00ff95),
          maskColor: const Color(0xD000000),
          backgroundColor: Colors.black,
          levels: 10,
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
  final LinkedList<CharWithBrightness> screenChars = LinkedList();
  final int _maxBrightness;
  int _maxRows = 10;
  int _buildId = 0;
  bool _dirty = true;

  get buildId => _buildId;

  Iterable<CharWithBrightness> getLevel(int level) =>
      screenChars.where((ch) => ch.supportLevel(level));

  void resize(int columns, int rows) {
    _maxRows = rows;

    while (columns > _sizes.length) {
      _sizes.add(0);
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
    if (screenChars.isEmpty) {
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
    }
  }

  void _addNewChars() {
    for (var c = 0; c < _sizes.length; c++) {
      if (_sizes[c] > _maxRows || random.nextDouble() > 0.975) {
        _sizes[c] = 0;
      }
      _sizes[c]++;

      screenChars.add(
        CharWithBrightness(
          asciiChars[random.nextInt(asciiChars.length)],
          _maxBrightness,
          c,
          _sizes[c],
        ),
      );
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
