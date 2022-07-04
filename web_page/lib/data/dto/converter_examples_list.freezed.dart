// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target

part of 'converter_examples_list.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

ConverterExamplesList _$ConverterExamplesListFromJson(
    Map<String, dynamic> json) {
  return _ConverterExamplesList.fromJson(json);
}

/// @nodoc
mixin _$ConverterExamplesList {
  List<ConverterExample> get examples => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $ConverterExamplesListCopyWith<ConverterExamplesList> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $ConverterExamplesListCopyWith<$Res> {
  factory $ConverterExamplesListCopyWith(ConverterExamplesList value,
          $Res Function(ConverterExamplesList) then) =
      _$ConverterExamplesListCopyWithImpl<$Res>;
  $Res call({List<ConverterExample> examples});
}

/// @nodoc
class _$ConverterExamplesListCopyWithImpl<$Res>
    implements $ConverterExamplesListCopyWith<$Res> {
  _$ConverterExamplesListCopyWithImpl(this._value, this._then);

  final ConverterExamplesList _value;
  // ignore: unused_field
  final $Res Function(ConverterExamplesList) _then;

  @override
  $Res call({
    Object? examples = freezed,
  }) {
    return _then(_value.copyWith(
      examples: examples == freezed
          ? _value.examples
          : examples // ignore: cast_nullable_to_non_nullable
              as List<ConverterExample>,
    ));
  }
}

/// @nodoc
abstract class _$$_ConverterExamplesListCopyWith<$Res>
    implements $ConverterExamplesListCopyWith<$Res> {
  factory _$$_ConverterExamplesListCopyWith(_$_ConverterExamplesList value,
          $Res Function(_$_ConverterExamplesList) then) =
      __$$_ConverterExamplesListCopyWithImpl<$Res>;
  @override
  $Res call({List<ConverterExample> examples});
}

/// @nodoc
class __$$_ConverterExamplesListCopyWithImpl<$Res>
    extends _$ConverterExamplesListCopyWithImpl<$Res>
    implements _$$_ConverterExamplesListCopyWith<$Res> {
  __$$_ConverterExamplesListCopyWithImpl(_$_ConverterExamplesList _value,
      $Res Function(_$_ConverterExamplesList) _then)
      : super(_value, (v) => _then(v as _$_ConverterExamplesList));

  @override
  _$_ConverterExamplesList get _value =>
      super._value as _$_ConverterExamplesList;

  @override
  $Res call({
    Object? examples = freezed,
  }) {
    return _then(_$_ConverterExamplesList(
      examples: examples == freezed
          ? _value._examples
          : examples // ignore: cast_nullable_to_non_nullable
              as List<ConverterExample>,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_ConverterExamplesList implements _ConverterExamplesList {
  const _$_ConverterExamplesList(
      {required final List<ConverterExample> examples})
      : _examples = examples;

  factory _$_ConverterExamplesList.fromJson(Map<String, dynamic> json) =>
      _$$_ConverterExamplesListFromJson(json);

  final List<ConverterExample> _examples;
  @override
  List<ConverterExample> get examples {
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(_examples);
  }

  @override
  String toString() {
    return 'ConverterExamplesList(examples: $examples)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_ConverterExamplesList &&
            const DeepCollectionEquality().equals(other._examples, _examples));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode =>
      Object.hash(runtimeType, const DeepCollectionEquality().hash(_examples));

  @JsonKey(ignore: true)
  @override
  _$$_ConverterExamplesListCopyWith<_$_ConverterExamplesList> get copyWith =>
      __$$_ConverterExamplesListCopyWithImpl<_$_ConverterExamplesList>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_ConverterExamplesListToJson(this);
  }
}

abstract class _ConverterExamplesList implements ConverterExamplesList {
  const factory _ConverterExamplesList(
          {required final List<ConverterExample> examples}) =
      _$_ConverterExamplesList;

  factory _ConverterExamplesList.fromJson(Map<String, dynamic> json) =
      _$_ConverterExamplesList.fromJson;

  @override
  List<ConverterExample> get examples => throw _privateConstructorUsedError;
  @override
  @JsonKey(ignore: true)
  _$$_ConverterExamplesListCopyWith<_$_ConverterExamplesList> get copyWith =>
      throw _privateConstructorUsedError;
}
