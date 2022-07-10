import 'package:flutter/material.dart';
import 'package:web_page/ui/pages/app_routes.dart';
import 'package:web_page/ui/pages/examples_page/examples_page.dart';
import 'package:web_page/ui/pages/main_page/main_page.dart';

class App extends StatelessWidget {
  const App({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Ascii converter',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MainPage(),
      routes: {
        AppRoutes.examplesPage: (_) => const ExamplesPage(),
      },
    );
  }
}