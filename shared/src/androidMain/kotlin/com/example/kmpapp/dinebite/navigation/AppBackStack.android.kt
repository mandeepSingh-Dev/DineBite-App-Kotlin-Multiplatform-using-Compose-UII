package com.example.kmpapp.dinebite.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

@Composable
actual fun rememberAppBackStack(): NavBackStack<NavKey> {
    return remember { NavBackStack(DineBiteScreens.Splash) }
}
