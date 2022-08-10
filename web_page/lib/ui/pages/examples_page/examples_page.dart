import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:web_page/domain/service/converter_example_service.dart';
import 'package:web_page/ui/pages/examples_page/examples_page_widget.dart';
import 'package:web_page/ui/pages/examples_page/examples_page_widget_model.dart';

class ExamplesPage extends StatelessWidget {
  const ExamplesPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final service = context.read<ConverterExampleService>();

    return BlocProvider(
      create: (_) => ExamplesPageWidgetModel(service)..init(),
      child: const ExamplesPageWidget(),
    );
  }
}
