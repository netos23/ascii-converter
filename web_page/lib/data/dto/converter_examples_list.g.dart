// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'converter_examples_list.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_ConverterExamplesList _$$_ConverterExamplesListFromJson(
        Map<String, dynamic> json) =>
    _$_ConverterExamplesList(
      examples: (json['examples'] as List<dynamic>)
          .map((e) => ConverterExample.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_ConverterExamplesListToJson(
        _$_ConverterExamplesList instance) =>
    <String, dynamic>{
      'examples': instance.examples,
    };
