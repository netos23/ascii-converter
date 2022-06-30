import 'package:flutter/material.dart';

class DashButton extends StatelessWidget {
  const DashButton({
    Key? key,
    required this.child,
    required this.onPressed,
    required this.background,
  }) : super(key: key);

  final Widget child;
  final VoidCallback onPressed;
  final Color background;

  @override
  Widget build(BuildContext context) {
    return ElevatedButton(
      style: ButtonStyle(
          backgroundColor:
              MaterialStateColor.resolveWith((states) => background)),
      onPressed: onPressed,
      child: child,
    );
  }
}
