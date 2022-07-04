import 'package:flutter/material.dart';
import 'package:web_page/ui/widgets/matrix_page.dart';
import 'package:web_page/ui/theme/color_theme.dart' as color_theme;
import 'package:web_page/ui/theme/text_theme.dart' as text_theme;

class MatrixPageWithCard extends StatelessWidget {
  final List<Widget> children;



  const MatrixPageWithCard({
    Key? key,
    required this.children,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MatrixPage(
      landscapeLayout: _buildLandscape(),
      portraitLayout: _buildPortrait(),
    );
  }

  Widget _buildPortrait() {
    return _buildBody(mainAxisSize: MainAxisSize.max);
  }

  Padding _buildLandscape() {
    return Padding(
      padding: const EdgeInsets.all(50.0),
      child: _buildBody(
        mainAxisSize: MainAxisSize.min,
      ),
    );
  }

  Widget _buildBody({
    required MainAxisSize mainAxisSize,
  }) {
    return Container(
      color: color_theme.darkCard,
      child: Padding(
        padding: const EdgeInsets.all(10.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          mainAxisSize: mainAxisSize,
          children: children,
        ),
      ),
    );
  }
}
