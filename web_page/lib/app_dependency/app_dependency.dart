import 'package:web_page/data/repository/converter_examples_repository.dart';
import 'package:web_page/domain/service/converter_example_service.dart';

class AppDependency {
  final String examplesConfigPath;

  late final ConverterExampleService exampleService;

  AppDependency({
    required this.examplesConfigPath,
  });

  Future<void> init() async {
    final exampleRepository = ConverterExamplesRepository(examplesConfigPath);
    exampleService = ConverterExampleService(exampleRepository);
  }
}
