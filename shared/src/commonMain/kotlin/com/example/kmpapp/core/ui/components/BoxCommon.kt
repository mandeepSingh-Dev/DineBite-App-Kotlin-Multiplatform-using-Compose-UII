package com.example.kmpapp.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.example.kmpapp.core.ui.theme.hamsafar_bg_color

@Composable
fun BoxCommon(
    isLoading: Boolean = false,
    modifier: Modifier = Modifier,
    containerColor: Color = hamsafar_bg_color,
    containerBrush: Brush? = null,
    contentAlignment: Alignment = Alignment.TopStart,
    isAppearanceLightStatusBars: Boolean = true,
    isAppearanceLightNavigationBars: Boolean = true,
    showBackgroundImage: Boolean = false,
    backgroundImage: Painter? = null,
    isLoaderTappable: Boolean = false,
    bodyContent: @Composable BoxScope.() -> Unit,
) {
    SetStatusBarTextColor(isAppearanceLightStatusBars)
    SetNavigationBarTextColor(isAppearanceLightNavigationBars)

    val localKeyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                localKeyboardController?.hide()
            },
        contentAlignment = contentAlignment,
    ) {
        val backgroundModifier = if (containerBrush != null) {
            Modifier.background(brush = containerBrush)
        } else {
            Modifier.background(color = containerColor)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .then(backgroundModifier)
        ) {
            if (showBackgroundImage && backgroundImage != null) {
                Image(
                    painter = backgroundImage,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }
            bodyContent()
            ProgressLoader(isLoading = isLoading, isTappable = isLoaderTappable)
        }
    }
}

@Composable
fun BoxCommon2(
    isLoading: Boolean = false,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.White,
    containerBrush: Brush? = null,
    contentAlignment: Alignment = Alignment.TopStart,
    bodyContent: @Composable BoxScope.() -> Unit,
) {
    val localKeyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            localKeyboardController?.hide()
        },
        contentAlignment = contentAlignment,
    ) {
        val backgroundModifier = if (containerBrush != null) {
            Modifier.background(brush = containerBrush)
        } else {
            Modifier.background(color = containerColor)
        }

        Box(
            modifier = Modifier.then(backgroundModifier)
        ) {
            bodyContent()
            ProgressLoader(isLoading = isLoading)
        }
    }
}
