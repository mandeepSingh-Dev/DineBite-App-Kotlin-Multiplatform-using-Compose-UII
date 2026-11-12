package com.example.kmpapp.feature.auth.user.navigation.authGraph

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.kmpapp.core.navigation.UserScreens
import com.example.kmpapp.feature.auth.user.ui.screens.AgeConfirmationScreen
import com.example.kmpapp.feature.auth.user.ui.screens.LoginUserScreen

fun EntryProviderScope<NavKey>.authGraph(backStack: NavBackStack<NavKey>) {
    entry<UserScreens.AuthScreens.AgeConfirmationScreen> { key ->
        AgeConfirmationScreen(backStack = backStack, selectedUserType = key.selectedUserType)
    }
    entry<UserScreens.AuthScreens.LoginScreen> {
        LoginUserScreen(backStack = backStack)
    }
}
