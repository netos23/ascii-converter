import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:web_page/ui/widgets/dash_button.dart';
import 'package:web_page/ui/widgets/matrix_background.dart';
import 'package:web_page/ui/theme/color_theme.dart' as color_theme;
import 'package:web_page/ui/theme/text_theme.dart' as text_theme;

class ResultPage extends StatelessWidget {
  const ResultPage({Key? key}) : super(key: key);

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
                      child: _buildBody(mainAxisSize: MainAxisSize.min),
                    )
                  : _buildBody(mainAxisSize: MainAxisSize.max);
            },
          ),
        ),
      ),
    );
  }

  Container _buildBody({
    required MainAxisSize mainAxisSize,
  }) {
    return Container(
      color: color_theme.darkCard,
      child: _buildAboutArticle(
        mainAxisSize: mainAxisSize,
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
            flex: 1,
            child: FittedBox(
              child: Text(
                'DOWNLOAD IMAGE',
                style: text_theme.headline1,
                textAlign: TextAlign.center,
                maxLines: 2,
              ),
            ),
          ),
          Expanded(
            flex: 5,
            child: Image.asset('assets/ex.png'),
          ),
          Spacer(),
          Flexible(
            child: DashButton(
              onPressed: () {},
              background: color_theme.primary,
              child: const Text(
                'Download',
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
