import 'package:flutter/material.dart';

class DashButton extends StatelessWidget {
  const DashButton({
    Key? key,
    required this.child,
    required this.onPressed,
    this.background,
  }) : super(key: key);

  final Widget child;
  final VoidCallback onPressed;
  final Color? background;

  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: onPressed,
      child: Container(
        color: background,
        child: child,
      ),
    );
  }
}
