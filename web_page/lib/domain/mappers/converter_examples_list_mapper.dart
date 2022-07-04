import 'package:web_page/data/dto/converter_examples_list.dart';
import 'package:web_page/domain/model/converter_example.dart';
import 'converter_example_mapper.dart';

Iterable<ConverterExample> fromConverterExamplesListDto(
    ConverterExamplesList examplesList,
    ) {
  return examplesList.examples.map(fromConverterExampleDto);
}
