import 'dart:async';

import 'package:web_page/domain/model/converter_example.dart';

class ExamplesPageModel{
  final Future<List<ConverterExample>> examples;

  ExamplesPageModel(this.examples);
}