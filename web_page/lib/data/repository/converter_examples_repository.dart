import 'dart:convert';

import 'package:flutter/services.dart' show rootBundle;
import 'package:web_page/data/dto/converter_examples_list.dart';
import 'package:web_page/domain/repository/converter_repository_example.dart';

class ConverterExamplesRepository implements IConverterExamplesRepository {
  final String configPath;

  ConverterExamplesRepository(this.configPath);

  @override
  Future<ConverterExamplesList> loadConverterExamples() async {
    final examplesConfig = await rootBundle.loadString(configPath);

    return ConverterExamplesList.fromJson(jsonDecode(examplesConfig));
  }
}
