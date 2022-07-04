// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target

part of 'converter_example.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

ConverterExample _$ConverterExampleFromJson(Map<String, dynamic> json) {
  return _ConverterExample.fromJson(json);
}

/// @nodoc
mixin _$ConverterExample {
  String get before => throw _privateConstructorUsedError;
  String get after => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $ConverterExampleCopyWith<ConverterExample> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $ConverterExampleCopyWith<$Res> {
  factory $ConverterExampleCopyWith(
          ConverterExample value, $Res Function(ConverterExample) then) =
      _$ConverterExampleCopyWithImpl<$Res>;
  $Res call({String before, String after});
}

/// @nodoc
class _$ConverterExampleCopyWithImpl<$Res>
    implements $ConverterExampleCopyWith<$Res> {
  _$ConverterExampleCopyWithImpl(this._value, this._then);

  final ConverterExample _value;
  // ignore: unused_field
  final $Res Function(ConverterExample) _then;

  @override
  $Res call({
    Object? before = freezed,
    Object? after = freezed,
  }) {
    return _then(_value.copyWith(
      before: before == freezed
          ? _value.before
          : before // ignore: cast_nullable_to_non_nullable
              as String,
      after: after == freezed
          ? _value.after
          : after // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc
abstract class _$$_ConverterExampleCopyWith<$Res>
    implements $ConverterExampleCopyWith<$Res> {
  factory _$$_ConverterExampleCopyWith(
          _$_ConverterExample value, $Res Function(_$_ConverterExample) then) =
      __$$_ConverterExampleCopyWithImpl<$Res>;
  @override
  $Res call({String before, String after});
}

/// @nodoc
class __$$_ConverterExampleCopyWithImpl<$Res>
    extends _$ConverterExampleCopyWithImpl<$Res>
    implements _$$_ConverterExampleCopyWith<$Res> {
  __$$_ConverterExampleCopyWithImpl(
      _$_ConverterExample _value, $Res Function(_$_ConverterExample) _then)
      : super(_value, (v) => _then(v as _$_ConverterExample));

  @override
  _$_ConverterExample get _value => super._value as _$_ConverterExample;

  @override
  $Res call({
    Object? before = freezed,
    Object? after = freezed,
  }) {
    return _then(_$_ConverterExample(
      before: before == freezed
          ? _value.before
          : before // ignore: cast_nullable_to_non_nullable
              as String,
      after: after == freezed
          ? _value.after
          : after // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_ConverterExample implements _ConverterExample {
  const _$_ConverterExample({required this.before, required this.after});

  factory _$_ConverterExample.fromJson(Map<String, dynamic> json) =>
      _$$_ConverterExampleFromJson(json);

  @override
  final String before;
  @override
  final String after;

  @override
  String toString() {
    return 'ConverterExample(before: $before, after: $after)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_ConverterExample &&
            const DeepCollectionEquality().equals(other.before, before) &&
            const DeepCollectionEquality().equals(other.after, after));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      const DeepCollectionEquality().hash(before),
      const DeepCollectionEquality().hash(after));

  @JsonKey(ignore: true)
  @override
  _$$_ConverterExampleCopyWith<_$_ConverterExample> get copyWith =>
      __$$_ConverterExampleCopyWithImpl<_$_ConverterExample>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_ConverterExampleToJson(this);
  }
}

abstract class _ConverterExample implements ConverterExample {
  const factory _ConverterExample(
      {required final String before,
      required final String after}) = _$_ConverterExample;

  factory _ConverterExample.fromJson(Map<String, dynamic> json) =
      _$_ConverterExample.fromJson;

  @override
  String get before => throw _privateConstructorUsedError;
  @override
  String get after => throw _privateConstructorUsedError;
  @override
  @JsonKey(ignore: true)
  _$$_ConverterExampleCopyWith<_$_ConverterExample> get copyWith =>
      throw _privateConstructorUsedError;
}
