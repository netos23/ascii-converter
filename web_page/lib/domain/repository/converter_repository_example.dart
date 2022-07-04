import 'package:web_page/data/dto/converter_examples_list.dart';

abstract class IConverterExamplesRepository {

  Future<ConverterExamplesList> loadConverterExamples();
}
