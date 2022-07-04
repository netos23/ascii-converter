import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:web_page/domain/model/converter_example.dart';
import 'package:web_page/ui/pages/app_routes.dart';
import 'package:web_page/ui/pages/examples_page/examples_page_model.dart';
import 'package:web_page/ui/pages/examples_page/examples_page_widget_model.dart';
import 'package:web_page/ui/widgets/doted_text_button.dart';
import 'package:web_page/ui/theme/color_theme.dart' as color_theme;
import 'package:web_page/ui/theme/text_theme.dart' as text_theme;
import 'package:web_page/ui/widgets/matrix_page_with_card.dart';

class ExamplesPageWidget extends StatelessWidget {
  const ExamplesPageWidget({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MatrixPageWithCard(
      children: [
        Flexible(
          flex: 2,
          child: _buildTitle(),
        ),
        const Spacer(),
        const Expanded(
          flex: 10,
          child: ExamplesList(),
        ),
        const Spacer(),
        Flexible(
          child: _buildMainPageButton(context),
        )
      ],
    );
  }

  Widget _buildMainPageButton(BuildContext context) {
    return DotedTextButton(
      onPressed: () => Navigator.pushNamed(context, AppRoutes.home),
      text: 'Main Page',
    );
  }

  Text _buildTitle() {
    return Text(
      'ASCII CONVERTER EXAMPLES',
      style: text_theme.headline2,
      textAlign: TextAlign.center,
      maxLines: 3,
    );
  }
}

class ExamplesList extends StatelessWidget {
  const ExamplesList({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<ExamplesPageWidgetModel, ExamplesPageModel>(
      bloc: context.watch<ExamplesPageWidgetModel>(),
      builder: (context, futureItems) {
        return FutureBuilder<List<ConverterExample>>(
          future: futureItems.examples,
          builder: (context, snapshot) {
            if (snapshot.hasError) {
              // todo: error handle
              return const CircularProgressIndicator();
            }

            if (!snapshot.hasData) {
              return const CircularProgressIndicator();
            }
            final examples = snapshot.data;

            if (examples == null) {
              // todo: empty list
              return const CircularProgressIndicator();
            }

            return ListView.builder(
              clipBehavior: Clip.antiAlias,
              itemCount: examples.length,
              itemBuilder: (context, index) {
                return Example(example: examples[index]);
              },
            );
          },
        );
      },
    );
  }
}

class Example extends StatelessWidget {
  final ConverterExample example;

  const Example({
    Key? key,
    required this.example,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Padding(
        padding: const EdgeInsets.all(8.0),
        child: SizedBox(
          height: 500,
          child: OrientationBuilder(builder: (context, orientation) {
            print('reb $orientation');
            return Flex(
              mainAxisSize: MainAxisSize.min,
              direction: orientation == Orientation.landscape
                  ? Axis.horizontal
                  : Axis.vertical,
              children: [
                Expanded(
                  flex: 10,
                  child: NamedImage(
                    name: 'before',
                    asset: example.before,
                  ),
                ),
                const Spacer(),
                Expanded(
                  flex: 10,
                  child: NamedImage(
                    name: 'after',
                    asset: example.after,
                  ),
                ),
              ],
            );
          }),
        ),
      ),
    );
  }
}

class NamedImage extends StatelessWidget {
  const NamedImage({
    Key? key,
    required this.name,
    required this.asset,
  }) : super(key: key);

  final String name;
  final String asset;

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.start,
      children: [
        Expanded(
          flex: 7,
          child: Image.asset(
            asset,
          ),
        ),
        Expanded(
          child: Text(
            name,
            style: text_theme.regularText,
          ),
        ),
      ],
    );
  }
}
