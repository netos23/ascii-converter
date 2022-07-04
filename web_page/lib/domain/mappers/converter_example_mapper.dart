import 'package:web_page/data/dto/converter_example.dart' as dto;
import 'package:web_page/data/dto/converter_examples_list.dart';
import 'package:web_page/domain/model/converter_example.dart';

ConverterExample fromConverterExampleDto(dto.ConverterExample example) {
  return ConverterExample(
    before: example.before,
    after: example.after,
  );
}

