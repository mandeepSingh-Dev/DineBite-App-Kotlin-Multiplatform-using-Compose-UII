package com.example.kmpapp.dinebite.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack

@Composable
actual fun rememberAppBackStack(): NavBackStack<NavKey> = rememberNavBackStack(DineBiteScreens.Splash)
