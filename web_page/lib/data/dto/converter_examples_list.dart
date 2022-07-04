import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:web_page/data/dto/converter_example.dart';

part 'converter_examples_list.freezed.dart';
part 'converter_examples_list.g.dart';

@freezed
class ConverterExamplesList with _$ConverterExamplesList {
  const factory ConverterExamplesList({
    required List<ConverterExample> examples,
  }) = _ConverterExamplesList;

  factory ConverterExamplesList.fromJson(Map<String, dynamic> json) =>
      _$ConverterExamplesListFromJson(json);
}
