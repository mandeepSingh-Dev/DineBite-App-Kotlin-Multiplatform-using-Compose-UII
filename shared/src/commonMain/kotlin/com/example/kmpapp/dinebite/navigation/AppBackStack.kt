package com.example.kmpapp.dinebite.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

@Composable
expect fun rememberAppBackStack(): NavBackStack<NavKey>
