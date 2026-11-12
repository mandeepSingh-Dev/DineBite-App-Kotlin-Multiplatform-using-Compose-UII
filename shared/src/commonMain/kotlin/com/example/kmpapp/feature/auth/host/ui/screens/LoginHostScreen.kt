package com.example.kmpapp.feature.auth.host.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.kmpapp.core.navigation.AppScreens

@Composable
fun LoginHostScreen(
    backStack: NavBackStack<NavKey>
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Host Login Screen")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                backStack.removeLastOrNull()
                backStack.add(AppScreens.HomeBottomContainerHostScreen)
            }) {
                Text("Login as Host Successfully (Go to Host Home)")
            }
        }
    }
}
