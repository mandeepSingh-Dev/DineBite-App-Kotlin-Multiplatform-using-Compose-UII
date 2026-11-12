package com.example.kmpapp.dinebite.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

@Composable
actual fun rememberAppBackStack(): NavBackStack<NavKey> {
    val list = remember { mutableStateListOf<NavKey>(DineBiteScreens.Splash) }
    return list as NavBackStack<NavKey>
}
