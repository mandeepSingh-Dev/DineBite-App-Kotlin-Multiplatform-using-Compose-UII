package com.example.kmpapp.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface AppDatastore {
    val authTokenFlow: Flow<String?>
    val isOnboardingCompletedFlow: Flow<Boolean>
    val rememberMeFlow: Flow<Boolean>

    suspend fun saveAuthToken(token: String)
    suspend fun saveOnboardingCompleted(completed: Boolean)
    suspend fun saveRememberMe(remember: Boolean)
    suspend fun clearSession()
}

class AppDatastoreImpl(
    private val dataStore: DataStore<Preferences>
) : AppDatastore {

    private companion object {
        val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")
        val ONBOARDING_COMPLETED_KEY = booleanPreferencesKey("onboarding_completed")
        val REMEMBER_ME_KEY = booleanPreferencesKey("remember_me")
    }

    override val authTokenFlow: Flow<String?> = dataStore.data
        .map { preferences -> preferences[AUTH_TOKEN_KEY] }

    override val isOnboardingCompletedFlow: Flow<Boolean> = dataStore.data
        .map { preferences -> preferences[ONBOARDING_COMPLETED_KEY] ?: false }

    override val rememberMeFlow: Flow<Boolean> = dataStore.data
        .map { preferences -> preferences[REMEMBER_ME_KEY] ?: false }

    override suspend fun saveAuthToken(token: String) {
        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN_KEY] = token
        }
    }

    override suspend fun saveOnboardingCompleted(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[ONBOARDING_COMPLETED_KEY] = completed
        }
    }

    override suspend fun saveRememberMe(remember: Boolean) {
        dataStore.edit { preferences ->
            preferences[REMEMBER_ME_KEY] = remember
        }
    }

    override suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
