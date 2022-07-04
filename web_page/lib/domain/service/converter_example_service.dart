import 'dart:async';

import 'package:web_page/data/repository/converter_examples_repository.dart';
import 'package:web_page/domain/mappers/converter_examples_list_mapper.dart';
import 'package:web_page/domain/model/converter_example.dart';

class ConverterExampleService {
  final ConverterExamplesRepository _repository;
  late final Future<List<ConverterExample>> examples = _getExamples();

  ConverterExampleService(this._repository);

  Future<List<ConverterExample>> _getExamples() async {
    final dto = await _repository.loadConverterExamples();
    final converterExamplesList = fromConverterExamplesListDto(dto);

    return List.unmodifiable(converterExamplesList);
  }
}
