package com.example.kmpapp.feature.auth.host.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.kmpapp.core.navigation.HostScreens
import com.example.kmpapp.feature.auth.host.ui.screens.LoginHostScreen

fun EntryProviderScope<NavKey>.authHostGraph(backStack: NavBackStack<NavKey>) {
    entry<HostScreens.AuthHostScreens.LoginHostScreen> {
        LoginHostScreen(backStack = backStack)
    }
}
