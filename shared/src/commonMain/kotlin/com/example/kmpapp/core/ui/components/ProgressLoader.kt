package com.example.kmpapp.core.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.kmpapp.core.ui.theme.theme_color_1

@Composable
fun ProgressLoader(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    isTappable: Boolean = false,
    indicatorColor: Color = theme_color_1
) {
    AnimatedVisibility(
        visible = isLoading,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.2f))
                .then(
                    if (!isTappable) {
                        Modifier.clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            // Consume taps while loading
                        }
                    } else {
                        Modifier
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = indicatorColor
            )
        }
    }
}

@Composable
fun SetStatusBarTextColor(isAppearanceLightStatusBars: Boolean) {
    // Platform-specific status bar theming hook
}

@Composable
fun SetNavigationBarTextColor(isAppearanceLightNavigationBars: Boolean) {
    // Platform-specific navigation bar theming hook
}
