package com.example.kmpapp

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.kmpapp.core.ui.components.NetworkStatusBanner
import com.example.kmpapp.core.di.appModule
import com.example.kmpapp.dinebite.navigation.DineBiteScreens
import com.example.kmpapp.dinebite.navigation.dineBiteGraph
import com.example.kmpapp.dinebite.navigation.rememberAppBackStack
import org.koin.compose.KoinApplication
import org.koin.dsl.koinConfiguration

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@Composable
actual fun App() {



    val platformInterface = object : PlatformInterface{
        override fun getResult(presence: String) {
            "Android hai yo to"
        }

    }

    KoinApplication(configuration = koinConfiguration { modules(appModule) }) {
        MaterialTheme {
            val backstack = rememberAppBackStack()
            val snackBarHostState = remember { SnackbarHostState() }
            val context = LocalContext.current
            val activity = remember(context) { context.findActivity() }

            LaunchedEffect(backstack.lastOrNull()) {
                if (backstack.lastOrNull() is DineBiteScreens.Home) {
                    activity?.let { act ->
                        val permissions = mutableListOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            permissions.add(Manifest.permission.POST_NOTIFICATIONS)
                        }
                        ActivityCompat.requestPermissions(act, permissions.toTypedArray(), 100)
                    }
                }
            }

            Scaffold(
                snackbarHost = { SnackbarHost(snackBarHostState) },
                bottomBar = { NetworkStatusBanner() },
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                innerPadding
                NavDisplay(
                    backStack = backstack,
                    onBack = {
                        if (backstack.size > 1) backstack.removeLastOrNull()
                    },
                    entryDecorators = listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator()
                    ),
                    entryProvider = entryProvider {
                        dineBiteGraph(backstack)
                    }
                )
            }
        }
    }
}


