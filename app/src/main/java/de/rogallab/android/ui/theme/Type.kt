package de.rogallab.android.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import de.rogallab.android.R

private val FontName = FontFamily(
   Font(R.font.roboto_regular, FontWeight.W400),
)

private val FontName1= FontFamily(
   Font(R.font.montserrat_regular, FontWeight.W700),
)

val AppTypography = Typography(
   h5 = TextStyle(
      fontFamily = FontName,
      fontWeight = FontWeight.Normal,
      fontSize = 28.sp
   ),
   h6 = TextStyle(
      fontFamily = FontName,
      fontWeight = FontWeight.Normal,
      fontSize = 24.sp
   ),
   subtitle1 = TextStyle(
      fontFamily = FontName,
      fontSize = 18.sp
   ),
   subtitle2 = TextStyle(
      fontFamily = FontName,
      fontSize = 16.sp
   ),
   body1 = TextStyle(
      fontFamily = FontName,
      fontWeight = FontWeight.W500,
      fontSize = 18.sp
   ),
   body2 = TextStyle(
      fontFamily = FontName,
      fontSize = 16.sp
   ),
   button = TextStyle(
      fontFamily = FontName,
      fontSize = 18.sp
   ),
   caption = TextStyle(
      fontFamily = FontName,
      fontSize = 14.sp
   ),
   overline = TextStyle(
      fontFamily = FontName,
      fontSize = 14.sp
   )
)

val Typography = Typography(
   h4 = TextStyle(
      fontFamily = FontName,
      fontWeight = FontWeight.W600,
      fontSize = 30.sp
   ),
   h5 = TextStyle(
      fontFamily = FontName,
      fontWeight = FontWeight.W600,
      fontSize = 24.sp
   ),
   h6 = TextStyle(
      fontFamily = FontName,
      fontWeight = FontWeight.W600,
      fontSize = 20.sp
   ),
   subtitle1 = TextStyle(
      fontFamily = FontName,
      fontWeight = FontWeight.W600,
      fontSize = 16.sp
   ),
   subtitle2 = TextStyle(
      fontFamily = FontName,
      fontWeight = FontWeight.W500,
      fontSize = 14.sp
   ),
   body1 = TextStyle(
      fontFamily = FontName,
      fontWeight = FontWeight.Normal,
      fontSize = 16.sp
   ),
   body2 = TextStyle(
      fontFamily = FontName,
      fontSize = 14.sp
   ),
   button = TextStyle(
      fontFamily = FontName,
      fontWeight = FontWeight.W500,
      fontSize = 14.sp
   ),
   caption = TextStyle(
      fontFamily = FontName,
      fontWeight = FontWeight.Normal,
      fontSize = 12.sp
   ),
   overline = TextStyle(
      fontFamily = FontName,
      fontWeight = FontWeight.W500,
      fontSize = 12.sp
   )

)