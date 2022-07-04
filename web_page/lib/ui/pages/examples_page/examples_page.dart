import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:web_page/domain/service/converter_example_service.dart';
import 'package:web_page/ui/pages/app_routes.dart';
import 'package:web_page/ui/pages/examples_page/examples_page_widget.dart';
import 'package:web_page/ui/pages/examples_page/examples_page_widget_model.dart';
import 'package:web_page/ui/widgets/doted_text_button.dart';
import 'package:web_page/ui/widgets/matrix_page.dart';
import 'package:web_page/ui/theme/color_theme.dart' as color_theme;
import 'package:web_page/ui/theme/text_theme.dart' as text_theme;
import 'package:web_page/ui/widgets/matrix_page_with_card.dart';

class ExamplesPage extends StatelessWidget {
  const ExamplesPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final service = context.read<ConverterExampleService>();
    return BlocProvider(
      create: (_) => ExamplesPageWidgetModel.fromService(service),
      child: const ExamplesPageWidget(),
    );
  }
}
