package com.example.kmpapp.feature.splash.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.kmpapp.core.common.UserManager
import com.example.kmpapp.core.common.utils.AttachLifecycleObserver
import com.example.kmpapp.core.common.utils.CommonUIEvents
import com.example.kmpapp.core.common.utils.ObserveUIEvents
import com.example.kmpapp.core.ui.components.BoxCommon
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import kmpapp.shared.generated.resources.Res
import kmpapp.shared.generated.resources._1__splash_1__1_
import kmpapp.shared.generated.resources.kaho_ji_logoimage1

@Composable
fun SplashScreen(
    backStack : NavBackStack<NavKey>,
    splashViewModel: SplashViewModel = SplashViewModel(UserManager())
) {
    AttachLifecycleObserver { event ->
        when(event) {
            Lifecycle.Event.ON_RESUME -> {
                splashViewModel.startTimer()
            }
            else -> Unit
        }
    }

    ObserveUIEvents(splashViewModel.uiEvent) { event ->
        when(event) {
            is CommonUIEvents.NavigateTo -> {
                backStack.removeLastOrNull()
                backStack.add(event.navKey)
            }
        }
    }

    val logoScale = remember { Animatable(0.85f) }
    val logoAlpha = remember { Animatable(0f) }
    val textAlpha = remember { Animatable(0f) }
    val textOffsetY = remember { Animatable(20f) }

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

    LaunchedEffect(Unit) {
        launch {
            logoScale.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
            )
        }
        launch {
            logoAlpha.animateTo(targetValue = 1f, animationSpec = tween(durationMillis = 600))
        }
        delay(250)
        launch {
            textAlpha.animateTo(targetValue = 1f, animationSpec = tween(durationMillis = 600))
        }
        launch {
            textOffsetY.animateTo(targetValue = 0f, animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing))
        }
    }

    BoxCommon(modifier = Modifier.fillMaxSize()) {
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
                    }
            )

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
