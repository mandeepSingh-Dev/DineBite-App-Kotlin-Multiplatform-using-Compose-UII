package com.example.kmpapp.dinebite.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.kmpapp.dinebite.ui.*
import com.example.kmpapp.learning.LearningHubScreen

fun EntryProviderScope<NavKey>.dineBiteGraph(backStack: NavBackStack<NavKey>) {
    entry<DineBiteScreens.Splash> {
        DineBiteSplashScreen(backStack = backStack)
    }
    entry<DineBiteScreens.Welcome> {
        DineBiteWelcomeScreen(backStack = backStack)
    }
    entry<DineBiteScreens.Onboarding> {
        DineBiteOnboardingScreen(backStack = backStack)
    }
    entry<DineBiteScreens.Login> {
        DineBiteLoginScreen(backStack = backStack)
    }
    entry<DineBiteScreens.Signup> {
        DineBiteSignupScreen(backStack = backStack)
    }
    entry<DineBiteScreens.Home> {
        DineBiteHomeScreen(backStack = backStack)
    }
    entry<DineBiteScreens.Menu> {
        DineBiteMenuScreen(backStack = backStack)
    }
    entry<DineBiteScreens.TableReservation> {
        DineBiteTableReservationScreen(backStack = backStack)
    }
    entry<DineBiteScreens.Cart> {
        DineBiteCartScreen(backStack = backStack)
    }
    entry<DineBiteScreens.Checkout> {
        DineBiteCheckoutScreen(backStack = backStack)
    }
    entry<DineBiteScreens.OrderSuccess> {
        DineBiteOrderSuccessScreen(backStack = backStack)
    }
    entry<DineBiteScreens.Orders> {
        DineBiteOrdersScreen(backStack = backStack)
    }
    entry<DineBiteScreens.TrackOrder> {
        DineBiteTrackOrderScreen(backStack = backStack)
    }
    entry<DineBiteScreens.Notifications> {
        DineBiteNotificationScreen(backStack = backStack)
    }
    entry<DineBiteScreens.Profile> {
        DineBiteProfileScreen(backStack = backStack)
    }
    entry<DineBiteScreens.EditProfile> {
        DineBiteEditProfileScreen(backStack = backStack)
    }
    entry<DineBiteScreens.MyBookings> {
        DineBiteMyBookingsScreen(backStack = backStack)
    }
    entry<DineBiteScreens.MyAddresses> {
        DineBiteMyAddressesScreen(backStack = backStack)
    }
    entry<DineBiteScreens.AddAddress> {
        DineBiteAddAddressScreen(backStack = backStack)
    }
    entry<DineBiteScreens.MyReviews> {
        DineBiteMyReviewsScreen(backStack = backStack)
    }
    entry<DineBiteScreens.TermsConditions> {
        DineBiteTermsConditionsScreen(backStack = backStack)
    }
    entry<DineBiteScreens.PrivacyPolicy> {
        DineBitePrivacyPolicyScreen(backStack = backStack)
    }
    entry<DineBiteScreens.HelpSupport> {
        DineBiteHelpSupportScreen(backStack = backStack)
    }
    entry<DineBiteScreens.ContactUs> {
        DineBiteContactUsScreen(backStack = backStack)
    }
    entry<DineBiteScreens.LearningHub> {
        LearningHubScreen(backStack = backStack)
    }
}
