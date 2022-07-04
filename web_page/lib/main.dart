import 'package:flutter/material.dart';
import 'package:web_page/app_dependency/app_dependency.dart';
import 'package:web_page/app_dependency/app_wrapper.dart';
import 'package:web_page/ui/app.dart';
import 'package:web_page/ui/pages/app_routes.dart';
import 'package:web_page/ui/pages/examples_page/examples_page.dart';
import 'package:web_page/ui/pages/main_page/main_page.dart';
import 'package:web_page/ui/pages/result_page/result_page.dart';

import 'package:web_page/ui/widgets/matrix_background.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();

  final dependency = AppDependency(
    examplesConfigPath: 'examples/examples.json',
  );

  await dependency.init();

  runApp(
    AppWrapper(
      dependency: dependency,
      app: const App(),
    ),
  );
}
