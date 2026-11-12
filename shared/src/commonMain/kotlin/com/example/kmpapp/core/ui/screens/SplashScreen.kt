package com.example.kmpapp.core.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.kmpapp.core.ui.components.BoxCommon
import kmpapp.shared.generated.resources.Res
import kmpapp.shared.generated.resources._1__splash_1__1_
import kmpapp.shared.generated.resources.kaho_ji_logoimage1
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@Composable
fun SplashScreen(
    onNavigateNext: () -> Unit = {}
) {
    // 1. Initial Animatable values for Smooth Entrance
    val logoScale = remember { Animatable(0.85f) }
    val logoAlpha = remember { Animatable(0f) }
    val textAlpha = remember { Animatable(0f) }
    val textOffsetY = remember { Animatable(20f) }

    // 2. Infinite Transition for Smooth Breathing & Ambient Glow FX
    val infiniteTransition = rememberInfiniteTransition(label = "SplashInfiniteEffects")

    val floatingOffset by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "FloatingAnimation"
    )

    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "PulseAnimation"
    )

    // Smooth Entrance Sequence
    LaunchedEffect(Unit) {
        launch {
            logoScale.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 800,
                    easing = FastOutSlowInEasing
                )
            )
        }
        launch {
            logoAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 600)
            )
        }

        // Staggered smooth fade & slide-up for text
        delay(250)
        launch {
            textAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 600)
            )
        }
        launch {
            textOffsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(
                    durationMillis = 600,
                    easing = FastOutSlowInEasing
                )
            )
        }

        // Timer to navigate to next screen
        delay(2500)
        onNavigateNext()
    }

    BoxCommon(modifier = Modifier.fillMaxSize()) {
        // Background Image using KMP Compose Resources
        Image(
            painter = painterResource(Res.drawable._1__splash_1__1_),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Animated Logo with Ambient Soft Glow & Shadow
            Image(
                painter = painterResource(Res.drawable.kaho_ji_logoimage1),
                contentDescription = null,
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = logoScale.value * pulseScale
                        scaleY = logoScale.value * pulseScale
                        translationY = floatingOffset
                        alpha = logoAlpha.value
                        shadowElevation = 12.dp.toPx()
                        spotShadowColor = Color.Black.copy(alpha = 0.4f)
                        ambientShadowColor = Color.Black.copy(alpha = 0.2f)
                    }
            )

            // Animated Slogan Text with Smooth Slide & Fade
            Text(
                text = "Talk . Connect Enjoy",
                modifier = Modifier
                    .graphicsLayer {
                        alpha = textAlpha.value
                        translationY = textOffsetY.value + (floatingOffset * 0.4f)
                    }
            )
        }
    }
}
