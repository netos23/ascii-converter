import 'package:flutter/material.dart';
import 'package:web_page/ui/pages/config_page.dart';
import 'package:web_page/ui/pages/main_page.dart';
import 'package:web_page/ui/pages/result_page.dart';
import 'package:web_page/ui/routes.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MainPage(),
      routes: {
        configPageRoute : (_) => const ConfigPage(),
        resultPageRoute : (_) => const ResultPage(),
      },
    );
  }
}
