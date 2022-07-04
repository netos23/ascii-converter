import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:web_page/ui/app.dart';
import 'package:web_page/app_dependency/app_dependency.dart';

class AppWrapper extends StatelessWidget {
  final AppDependency dependency;
  final App app;

  const AppWrapper({
    Key? key,
    required this.dependency,
    required this.app,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MultiRepositoryProvider(
      providers: [
        RepositoryProvider(create: (_) => dependency.exampleService),
      ],
      child: app,
    );
  }
}
