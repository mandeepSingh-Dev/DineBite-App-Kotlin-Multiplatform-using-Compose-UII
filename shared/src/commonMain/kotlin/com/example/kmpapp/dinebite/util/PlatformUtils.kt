package com.example.kmpapp.dinebite.util

expect class NotificationHelper() {
    fun showNotification(title: String, message: String)
}

expect class LocationService() {
    fun requestLocationAndFetch(onLocationFetched: (String) -> Unit)
}
