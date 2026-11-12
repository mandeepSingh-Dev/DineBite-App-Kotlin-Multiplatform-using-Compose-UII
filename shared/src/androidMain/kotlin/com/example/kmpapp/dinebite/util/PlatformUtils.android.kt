package com.example.kmpapp.dinebite.util

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.Locale

object AndroidContextProvider {
    lateinit var appContext: Context
}

actual class NotificationHelper actual constructor() {
    @SuppressLint("MissingPermission")
    actual fun showNotification(title: String, message: String) {
        try {
            val context = AndroidContextProvider.appContext
            val channelId = "dinebite_notifications"
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(channelId, "DineBite Orders", NotificationManager.IMPORTANCE_HIGH)
                notificationManager.createNotificationChannel(channel)
            }
            val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)

            NotificationManagerCompat.from(context).notify((1000..9999).random(), builder.build())
        } catch (e: Exception) {
        }
    }
}

actual class LocationService actual constructor() {
    @SuppressLint("MissingPermission")
    actual fun requestLocationAndFetch(onLocationFetched: (String) -> Unit) {
        try {
            val context = AndroidContextProvider.appContext
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)



            if (location != null) {
                Log.d("LOCATION_A", "location not null")
                val geocoder = Geocoder(context, Locale.getDefault())
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    geocoder.getFromLocation(location.latitude, location.longitude, 1) { addresses ->
                        if (addresses.isNotEmpty()) {
                            val addr = addresses[0]
                            val locality = addr.locality ?: addr.subAdminArea ?: "New Delhi"
                            val subLocality = addr.subLocality ?: addr.featureName ?: ""
                            Log.d("LOCATION_A", locality)
                            onLocationFetched(if (subLocality.isNotEmpty() && subLocality != locality) "$subLocality, $locality" else locality)
                        } else {
                            Log.d("LOCATION_A", "no address")
                            onLocationFetched("Green Park, New Delhi")
                        }
                    }
                } else {
                    @Suppress("DEPRECATION")
                    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    if (!addresses.isNullOrEmpty()) {
                        val addr = addresses[0]
                        val locality = addr.locality ?: addr.subAdminArea ?: "New Delhi"
                        val subLocality = addr.subLocality ?: addr.featureName ?: ""

                        Log.d("LOCATION_A", locality + " 3" )
                        onLocationFetched(if (subLocality.isNotEmpty() && subLocality != locality) "$subLocality, $locality" else locality)
                    } else {
                      Log.d("LOCATION_A", "else 4")
                        onLocationFetched("Green Park, New Delhi")
                    }
                }
            } else {
                Log.d("LOCATION_A", "5")
                onLocationFetched("Green Park, New Delhi")
            }
        } catch (e: Exception) {
            Log.d("LOCATION_A", " 6 ${e.message}")
            onLocationFetched("Green Park, New Delhi")
        }
    }
}
