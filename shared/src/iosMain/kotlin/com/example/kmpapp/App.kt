package com.example.kmpapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.kmpapp.core.di.appModule
import com.example.kmpapp.dinebite.navigation.DineBiteScreens
import com.example.kmpapp.dinebite.navigation.rememberAppBackStack
import com.example.kmpapp.dinebite.ui.*
import org.koin.compose.KoinApplication
import org.koin.dsl.koinConfiguration

@Composable
actual fun App() {
    KoinApplication(configuration = koinConfiguration { modules(appModule) }) {
        MaterialTheme {
            val backstack = rememberAppBackStack()
            val snackBarHostState = remember { SnackbarHostState() }

            Scaffold(
                snackbarHost = { SnackbarHost(snackBarHostState) },
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                Box(modifier = Modifier.fillMaxSize()) {
                    when (backstack.lastOrNull()) {
                        is DineBiteScreens.Splash -> DineBiteSplashScreen(backstack)
                        is DineBiteScreens.Welcome -> DineBiteWelcomeScreen(backstack)
                        is DineBiteScreens.Onboarding -> DineBiteOnboardingScreen(backstack)
                        is DineBiteScreens.Login -> DineBiteLoginScreen(backstack)
                        is DineBiteScreens.Signup -> DineBiteSignupScreen(backstack)
                        is DineBiteScreens.Home -> DineBiteHomeScreen(backstack)
                        is DineBiteScreens.Menu -> DineBiteMenuScreen(backstack)
                        is DineBiteScreens.TableReservation -> DineBiteTableReservationScreen(backstack)
                        is DineBiteScreens.Cart -> DineBiteCartScreen(backstack)
                        is DineBiteScreens.Checkout -> DineBiteCheckoutScreen(backstack)
                        is DineBiteScreens.OrderSuccess -> DineBiteOrderSuccessScreen(backstack)
                        is DineBiteScreens.Orders -> DineBiteOrdersScreen(backstack)
                        is DineBiteScreens.TrackOrder -> DineBiteTrackOrderScreen(backstack)
                        is DineBiteScreens.Profile -> DineBiteProfileScreen(backstack)
                        is DineBiteScreens.EditProfile -> DineBiteEditProfileScreen(backstack)
                        is DineBiteScreens.MyBookings -> DineBiteMyBookingsScreen(backstack)
                        is DineBiteScreens.MyAddresses -> DineBiteMyAddressesScreen(backstack)
                        is DineBiteScreens.AddAddress -> DineBiteAddAddressScreen(backstack)
                        is DineBiteScreens.MyReviews -> DineBiteMyReviewsScreen(backstack)
                        is DineBiteScreens.TermsConditions -> DineBiteTermsConditionsScreen(backstack)
                        is DineBiteScreens.PrivacyPolicy -> DineBitePrivacyPolicyScreen(backstack)
                        is DineBiteScreens.HelpSupport -> DineBiteHelpSupportScreen(backstack)
                        is DineBiteScreens.ContactUs -> DineBiteContactUsScreen(backstack)
                        else -> DineBiteSplashScreen(backstack)
                    }
                }
            }
        }
    }
}
