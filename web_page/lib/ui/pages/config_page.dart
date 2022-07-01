import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:web_page/ui/routes.dart';
import 'package:web_page/ui/widgets/dash_button.dart';
import 'package:web_page/ui/widgets/matrix_background.dart';
import 'package:web_page/ui/theme/color_theme.dart' as color_theme;
import 'package:web_page/ui/theme/text_theme.dart' as text_theme;

class ConfigPage extends StatelessWidget {
  const ConfigPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: MatrixBackground(
        style: MatrixRainStyle.only(
          textColor: color_theme.primary,
          backgroundColor: color_theme.background,
          // todo: create theme component
          textStyle: GoogleFonts.cutiveMono(
            fontWeight: FontWeight.w400,
          ),
        ),
        child: Center(
          child: OrientationBuilder(
            builder: (context, orientation) {
              return orientation == Orientation.landscape
                  ? Padding(
                      padding: const EdgeInsets.all(50.0),
                      child: _buildBody(
                        mainAxisSize: MainAxisSize.min,
                        context: context,
                      ),
                    )
                  : _buildBody(
                      mainAxisSize: MainAxisSize.max,
                      context: context,
                    );
            },
          ),
        ),
      ),
    );
  }

  Container _buildBody({
    required MainAxisSize mainAxisSize,
    required BuildContext context,
  }) {
    return Container(
      color: color_theme.darkCard,
      child: _buildAboutArticle(
        mainAxisSize: mainAxisSize,
        context: context,
      ),
    );
  }

  Widget _buildAboutArticle({
    required MainAxisSize mainAxisSize,
    required BuildContext context,
  }) {
    return Padding(
      padding: const EdgeInsets.all(10.0),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        mainAxisSize: MainAxisSize.min,
        children: [
          Flexible(
            flex: 2,
            child: FittedBox(
              child: Text(
                'CONVERT IMAGE',
                style: text_theme.headline1,
                textAlign: TextAlign.center,
                maxLines: 2,
              ),
            ),
          ),
          Flexible(
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text(
                    'Selected file',
                    style: text_theme.text,
                  ),
                ),
                DashButton(
                  onPressed: () {},
                  background: color_theme.primary,
                  child: const Text(
                    'Select file',
                    style: TextStyle(
                      color: color_theme.darkText,
                    ),
                  ),
                ),
              ],
            ),
          ),
          Flexible(
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text(
                    'Output format',
                    style: text_theme.text,
                  ),
                ),
                DashButton(
                  onPressed: () {},
                  background: color_theme.primary,
                  child: const Text(
                    'text',
                    style: TextStyle(
                      color: color_theme.darkText,
                    ),
                  ),
                ),
              ],
            ),
          ),
          Flexible(
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text(
                    'Color theme',
                    style: text_theme.text,
                  ),
                ),
                DashButton(
                  onPressed: () {},
                  background: color_theme.primary,
                  child: const Text(
                    'gray',
                    style: TextStyle(
                      color: color_theme.darkText,
                    ),
                  ),
                ),
              ],
            ),
          ),
          Flexible(
            child: DashButton(
              onPressed: () => Navigator.pushNamed(context, resultPageRoute),
              background: color_theme.primary,
              child: const Text(
                'Convert',
                style: TextStyle(
                  color: color_theme.darkText,
                ),
              ),
            ),
          )
        ],
      ),
    );
  }
}
