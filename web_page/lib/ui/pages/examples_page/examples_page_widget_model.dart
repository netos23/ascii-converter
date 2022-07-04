
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:web_page/domain/service/converter_example_service.dart';
import 'package:web_page/ui/pages/examples_page/examples_page_model.dart';

class ExamplesPageWidgetModel extends Cubit<ExamplesPageModel> {
  factory ExamplesPageWidgetModel.fromService(ConverterExampleService service) {
    return ExamplesPageWidgetModel._(
      ExamplesPageModel(service.examples),
    );
  }

  ExamplesPageWidgetModel._(super.initialState);
}
