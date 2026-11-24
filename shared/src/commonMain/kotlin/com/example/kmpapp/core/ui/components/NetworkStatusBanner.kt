package com.example.kmpapp.core.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kmpapp.core.network.NetworkConnectivityObserver
import kotlinx.coroutines.delay

@Composable
fun NetworkStatusBanner() {
    val observer = remember { NetworkConnectivityObserver() }
    val isConnected by observer.isConnected.collectAsState()
    var showBanner by remember { mutableStateOf(false) }

    LaunchedEffect(isConnected) {
        showBanner = !isConnected
    }

    AnimatedVisibility(
        visible = showBanner || !isConnected,
        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (isConnected) Color(0xFF4CAF50) else Color(0xFFE53935))
                .padding(12.dp)
                .navigationBarsPadding(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = if (isConnected) "🟢 Back online" else "📴 No internet connection. Offline mode",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }
    }

    if (isConnected && showBanner) {
        LaunchedEffect(Unit) {
            delay(3000)
            showBanner = false
        }
    }
}
