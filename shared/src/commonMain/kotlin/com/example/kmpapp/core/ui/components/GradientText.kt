package com.example.kmpapp.core.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.kmpapp.core.ui.theme.HAFFER_TRIAL_BOLD
import com.example.kmpapp.core.ui.theme.HAFFER_TRIAL_MEDIUM
import com.example.kmpapp.core.ui.theme.brush7

@Composable
fun GradientText(text : String,
                 modifier : Modifier = Modifier,
                 fontFamily: FontFamily = HAFFER_TRIAL_BOLD,
                 fontSize: TextUnit = 24.sp,
                 brush: Brush = brush7(),
                 letterSpacing : TextUnit = TextUnit.Unspecified
) {
    Text(text = text,
        style = TextStyle(
            brush = brush,
            fontFamily = fontFamily,
            fontSize = fontSize,
            letterSpacing = letterSpacing
        ), modifier = modifier)
}

@Composable
fun TextWithAsterisk(
    modifier: Modifier = Modifier,
    text : String,
    fontSize: TextUnit = 15.sp,
){
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Black, fontFamily = HAFFER_TRIAL_MEDIUM, fontSize = fontSize)){
                append((text))
            }
            withStyle(style = SpanStyle(color = Color.Red, fontFamily = HAFFER_TRIAL_MEDIUM, fontSize = fontSize)){
                append(" *")
            }
        },
        fontFamily = HAFFER_TRIAL_MEDIUM, fontSize = 13.sp,
        color = Color.Black,
        modifier = modifier
    )
}
