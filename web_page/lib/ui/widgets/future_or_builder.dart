import 'dart:async';

import 'package:flutter/material.dart';

class FutureOrBuilder<T> extends StatelessWidget {
  final AsyncWidgetBuilder<T> builder;
  final FutureOr<T> futureOr;

  const FutureOrBuilder({
    Key? key,
    required this.builder,
    required this.futureOr,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return (futureOr is Future<T>)
        ? FutureBuilder(future: (futureOr as Future<T>), builder: builder)
        : builder(
            context,
            AsyncSnapshot<T>.withData(ConnectionState.done, futureOr as T),
          );
  }
}
