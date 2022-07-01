import 'package:flutter/material.dart';
import 'package:web_page/ui/widgets/primary_color_button.dart';
import 'package:web_page/ui/theme/color_theme.dart' as color_theme;
import 'package:web_page/ui/theme/text_theme.dart' as text_theme;
import 'package:web_page/ui/widgets/matrix_page.dart';

class MainPage extends StatelessWidget {
  const MainPage({Key? key}) : super(key: key);

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
          children: [
            Flexible(
              flex: 3,
              child: _buildTitle(),
            ),
            Flexible(
              flex: 5,
              child: _buildAboutArticle(),
            ),
            const Spacer(),
            Flexible(
              flex: 2,
              child: _buildHintArticle(),
            ),
            Flexible(
              flex: 2,
              child: _buildNavigationRow(),
            ),
          ],
        ),
      ),
    );
  }

  Text _buildHintArticle() {
    return Text(
      'Ascii converter is simple way to create ascii images.',
      style: text_theme.regularText,
      textAlign: TextAlign.center,
    );
  }

  Text _buildAboutArticle() {
    return Text(
      'Wake up Neo! ASCII art is a graphic design technique\n'
      'that uses computers for presentation and consists of pictures\n'
      'pieced together from the 95 printable characters\n'
      'defined by the ASCII Standard from 1963 and ASCII compliant\n'
      'character sets with proprietary extended characters',
      style: text_theme.regularText,
      textAlign: TextAlign.center,
    );
  }

  Text _buildTitle() {
    return Text(
      'ASCII CONVERTER',
      style: text_theme.headline1,
      textAlign: TextAlign.center,
      maxLines: 2,
    );
  }

  Widget _buildNavigationRow() {
    return Padding(
      padding: const EdgeInsets.all(20.0),
      child: Row(
        mainAxisSize: MainAxisSize.min,
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Flexible(
            child: DashedTextButton(
              onPressed: () {},
              text: 'Examples',
            ),
          ),
          const Spacer(),
          Flexible(
            child: DashedTextButton(
              onPressed: () {},
              text: 'Convert',
            ),
          ),
        ],
      ),
    );
  }
}
