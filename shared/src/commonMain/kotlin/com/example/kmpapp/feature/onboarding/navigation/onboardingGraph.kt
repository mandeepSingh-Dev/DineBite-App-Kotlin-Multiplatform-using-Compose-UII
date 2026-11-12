package com.example.kmpapp.feature.onboarding.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.kmpapp.core.navigation.AppScreens
import com.example.kmpapp.feature.onboarding.ui.OnBoardingScreen
import com.example.kmpapp.feature.onboarding.ui.UserTypeSelectionScreen

fun EntryProviderScope<NavKey>.onboardingGraph(backStack: NavBackStack<NavKey>) {
    entry<AppScreens.OnBoardingScreen> {
        OnBoardingScreen(backStack = backStack)
    }
    entry<AppScreens.UserTypeSelectionScreen> {
        UserTypeSelectionScreen(backStack = backStack)
    }
}
