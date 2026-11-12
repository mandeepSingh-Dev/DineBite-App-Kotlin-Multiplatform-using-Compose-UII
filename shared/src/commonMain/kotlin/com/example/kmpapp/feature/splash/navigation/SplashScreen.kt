package com.example.kmpapp.feature.splash.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.kmpapp.core.navigation.AppScreens
import com.example.kmpapp.feature.splash.ui.SplashScreen

fun EntryProviderScope<NavKey>.splashGraph(backStack: NavBackStack<NavKey>) {
    entry<AppScreens.SplashScreen> {
        SplashScreen(backStack = backStack)
    }
}
