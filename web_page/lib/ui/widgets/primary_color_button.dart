import 'package:dotted_border/dotted_border.dart';
import 'package:flutter/material.dart';
import 'package:web_page/ui/theme/color_theme.dart' as color_theme;
import 'package:web_page/ui/theme/text_theme.dart' as text_theme;

class DashedTextButton extends StatelessWidget {
  final String text;
  final VoidCallback onPressed;
  final Color background;
  final TextStyle? style;

  const DashedTextButton({
    Key? key,
    required this.text,
    required this.onPressed,
    this.background = color_theme.primary,
    this.style,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ElevatedButton(
      style: ButtonStyle(
        padding: MaterialStateProperty.all(
          const EdgeInsets.symmetric(
            vertical: 15,
            horizontal: 5,
          ),
        ),
        backgroundColor: MaterialStateColor.resolveWith((states) => background),
        elevation: MaterialStateProperty.all(0),
      ),
      onPressed: onPressed,
      child: DottedBorder(
        child: Text(
          text,
          style: style ?? text_theme.regularTextDark,
        ),
      ),
    );
  }
}
