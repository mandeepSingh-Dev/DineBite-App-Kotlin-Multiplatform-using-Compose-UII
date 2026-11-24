package com.example.kmpapp.dinebite.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface DineBiteScreens : NavKey {
    @Serializable
    object Splash : DineBiteScreens

    @Serializable
    object Welcome : DineBiteScreens

    @Serializable
    object Onboarding : DineBiteScreens

    @Serializable
    object Login : DineBiteScreens

    @Serializable
    object Signup : DineBiteScreens

    @Serializable
    object Home : DineBiteScreens

    @Serializable
    object Menu : DineBiteScreens

    @Serializable
    object TableReservation : DineBiteScreens

    @Serializable
    object Cart : DineBiteScreens

    @Serializable
    object Checkout : DineBiteScreens

    @Serializable
    object OrderSuccess : DineBiteScreens

    @Serializable
    object Orders : DineBiteScreens

    @Serializable
    object TrackOrder : DineBiteScreens

    @Serializable
    object Notifications : DineBiteScreens

    @Serializable
    object Profile : DineBiteScreens

    @Serializable
    object EditProfile : DineBiteScreens

    @Serializable
    object MyBookings : DineBiteScreens

    @Serializable
    object MyAddresses : DineBiteScreens

    @Serializable
    object AddAddress : DineBiteScreens

    @Serializable
    object MyReviews : DineBiteScreens

    @Serializable
    object TermsConditions : DineBiteScreens

    @Serializable
    object PrivacyPolicy : DineBiteScreens

    @Serializable
    object HelpSupport : DineBiteScreens

    @Serializable
    object ContactUs : DineBiteScreens

    @Serializable
    object LearningHub : DineBiteScreens
}
