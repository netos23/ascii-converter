import 'package:freezed_annotation/freezed_annotation.dart';

part 'converter_example.freezed.dart';

part 'converter_example.g.dart';

@freezed
class ConverterExample with _$ConverterExample {
  const factory ConverterExample({
    required String before,
    required String after,
  }) = _ConverterExample;

  factory ConverterExample.fromJson(Map<String, dynamic> json) =>
      _$ConverterExampleFromJson(json);
}
