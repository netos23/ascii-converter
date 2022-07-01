import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:web_page/ui/theme/color_theme.dart' as color_theme;
import 'package:web_page/ui/theme/text_theme.dart' as text_theme;

import 'matrix_background.dart';

class MatrixPage extends StatelessWidget {
  final Widget landscapeLayout;
  final Widget portraitLayout;

  const MatrixPage({
    Key? key,
    required this.landscapeLayout,
    required this.portraitLayout,
  }) : super(key: key);

  const MatrixPage.singleLayout({
    Key? key,
    required Widget layout,
  }) : this(
          key: key,
          landscapeLayout: layout,
          portraitLayout: layout,
        );

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: MatrixBackground(
        style: MatrixRainStyle.only(
          textColor: color_theme.primary,
          backgroundColor: color_theme.background,
          textStyle: GoogleFonts.cutiveMono(
            fontWeight: FontWeight.w400,
          ),
        ),
        child: Center(
          child: OrientationBuilder(builder: (context, orientation) {
            return orientation == Orientation.landscape
                ? landscapeLayout
                : portraitLayout;
          }),
        ),
      ),
    );
  }
}
