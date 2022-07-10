
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:web_page/domain/service/converter_example_service.dart';
import 'package:web_page/ui/pages/examples_page/examples_page_model.dart';

class ExamplesPageWidgetModel extends Cubit<ExamplesPageModel> {
  final ConverterExampleService service;

  ExamplesPageWidgetModel(this.service) : super(ExamplesPageModel(null));

  Future<void> init() async{
    final examples = await service.examples;
    emit(ExamplesPageModel(examples));
  }
}
