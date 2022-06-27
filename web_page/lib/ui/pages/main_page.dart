import 'package:flutter/material.dart';
import 'package:web_page/ui/widgets/dash_button.dart';
import 'package:web_page/ui/widgets/matrix_background.dart';
import 'package:web_page/ui/theme/color_theme.dart' as color_theme;
import 'package:web_page/ui/theme/text_theme.dart' as text_theme;

class MainPage extends StatelessWidget {
  const MainPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: MatrixBackground(
        style: const MatrixRainStyle.only(
          textColor: color_theme.primary,
          backgroundColor: color_theme.background,
        ),
        child: Center(
          child: OrientationBuilder(builder: (context, orientation) {
            return orientation == Orientation.landscape
                ? Padding(
                    padding: const EdgeInsets.all(50.0),
                    child: _buildBody(
                      mainAxisSize: MainAxisSize.min,
                    ),
                  )
                : _buildBody(
                    mainAxisSize: MainAxisSize.max,
                  );
          }),
        ),
      ),
    );
  }

  Container _buildBody({
    required MainAxisSize mainAxisSize,
  }) {
    return Container(
      color: color_theme.darkCard,
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          Flexible(
            flex: 3,
            child: _buildAboutArticle(
              mainAxisSize: mainAxisSize,
            ),
          ),
          Flexible(
            flex: 2,
            child: _buildButtonsRow(),
          ),
        ],
      ),
    );
  }

  Widget _buildButtonsRow() {
    return Padding(
      padding: const EdgeInsets.all(20.0),
      child: Row(
        children: [
          DashButton(
            child: Text(
              'Exampeles',
            ),
            onPressed: () {},
          ),
          DashButton(
            child: Text(
              'Convert',
            ),
            onPressed: () {},
          ),
        ],
      ),
    );
  }

  Widget _buildAboutArticle({
    required MainAxisSize mainAxisSize,
  }) {
    return Padding(
      padding: const EdgeInsets.all(10.0),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        mainAxisSize: MainAxisSize.min,
        children: [
          Flexible(
            flex: 3,
            child: FittedBox(
              child: Text(
                'ASCII CONVERTER',
                style: text_theme.headline1,
                textAlign: TextAlign.center,
                maxLines: 2,
              ),
            ),
          ),
          Flexible(
            flex: 5,
            child: FittedBox(
              child: Text(
                'Wake up Neo! ASCII art is a graphic design technique\n'
                'that uses computers for presentation and consists of pictures\n'
                'pieced together from the 95 printable characters\n'
                'defined by the ASCII Standard from 1963 and ASCII compliant\n'
                'character sets with proprietary extended characters',
                style: text_theme.headline2,
                textAlign: TextAlign.center,
                textDirection: TextDirection.ltr,
              ),
            ),
          ),
          const Spacer(),
          Flexible(
            child: FittedBox(
              child: Text(
                'Ascii converter is simple way to create ascii images.',
                style: text_theme.headline2,
                textAlign: TextAlign.center,
              ),
            ),
          ),
        ],
      ),
    );
  }
}
