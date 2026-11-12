package com.example.kmpapp.dinebite.util

import platform.AVFoundation.AVCaptureFlashModeOn
import platform.CoreLocation.*
import platform.Foundation.*
import platform.UserNotifications.*

actual class NotificationHelper {
    actual fun showNotification(title: String, message: String) {
        val center = UNUserNotificationCenter.currentNotificationCenter()


        NSUserDefaults.standardUserDefaults

        center.requestAuthorizationWithOptions(
            options = UNAuthorizationOptionAlert or UNAuthorizationOptionSound or UNAuthorizationOptionBadge
        ) { granted, _ ->
            if (granted) {
                val content = UNMutableNotificationContent().apply {
                    setTitle(title)
                    setBody(message)
                    setSound(UNNotificationSound.defaultSound)
                }
                val request = UNNotificationRequest.requestWithIdentifier(
                    identifier = NSUUID().UUIDString(),
                    content = content,
                    trigger = null
                )
                center.addNotificationRequest(request, null)
            }
        }
    }
}

actual class LocationService {
    actual fun requestLocationAndFetch(onLocationFetched: (String) -> Unit) {
        try {
            val manager = CLLocationManager()
            manager.requestWhenInUseAuthorization()
            val location = manager.location
            if (location != null) {
                val coder = CLGeocoder()
                coder.reverseGeocodeLocation(location) { placemarks, error ->
                    if (error == null && !placemarks.isNullOrEmpty()) {
                        val placemark = placemarks[0] as CLPlacemark
                        val locality = placemark.locality ?: "New Delhi"
                        val subLocality = placemark.subLocality ?: ""
                        val address = if (subLocality.isNotEmpty() && subLocality != locality) "$subLocality, $locality" else locality
                        onLocationFetched(address)
                    } else {
                        onLocationFetched("Green Park, New Delhi")
                    }
                }
            } else {
                onLocationFetched("Green Park, New Delhi")
            }
        } catch (e: Exception) {
            onLocationFetched("Green Park, New Delhi")
        }
    }
}
