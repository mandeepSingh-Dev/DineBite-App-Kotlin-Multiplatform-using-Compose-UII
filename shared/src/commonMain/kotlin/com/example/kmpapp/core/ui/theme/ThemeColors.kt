package com.example.kmpapp.core.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

// Theme Colors
val theme_color_1 = Color(0xFF6C5CE7)
val theme_color_2 = Color(0xFFA29BFE)
val theme_color_3 = Color(0xFF74B9FF)
val theme_color_9 = Color(0xFF0984E3)
val hamsafar_bg_color = Color(0xFFF8F9FA)
val grey_5 = Color(0xFFD1D5DB)

// Brushes
fun brush2() = Brush.horizontalGradient(
    colors = listOf(theme_color_1, theme_color_2)
)

fun brush7() = Brush.horizontalGradient(
    colors = listOf(theme_color_1, theme_color_2, theme_color_3)
)

fun brushTransparent() = Brush.horizontalGradient(
    colors = listOf(Color.Transparent, Color.Transparent)
)

// Shapes
val Shape_12 = RoundedCornerShape(12.dp)
val Shape_15 = RoundedCornerShape(15.dp)
val Shape_20 = RoundedCornerShape(20.dp)

// Fonts (defaults to System font; custom fonts can be configured via Compose Resources)
val HAFFER_TRIAL_REGULAR: FontFamily = FontFamily.Default
val HAFFER_TRIAL_MEDIUM: FontFamily = FontFamily.Default
val HAFFER_TRIAL_SEMI_BOLD: FontFamily = FontFamily.Default
val HAFFER_TRIAL_BOLD: FontFamily = FontFamily.Default
