package com.example.kmpapp.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.example.kmpapp.dinebite.util.AndroidContextProvider

actual fun createDataStore(): DataStore<Preferences> {
    return PreferenceDataStoreFactory.create(
        produceFile = {
            AndroidContextProvider.appContext.filesDir.resolve("dinebite_prefs.preferences_pb")
        }
    )
}
