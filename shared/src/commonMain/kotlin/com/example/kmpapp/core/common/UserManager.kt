package com.example.kmpapp.core.common

import com.example.kmpapp.core.common.utils.enums.UserType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserManager {
    private val _userType = MutableStateFlow(UserType.USER.value)
    val userType = _userType.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedInState = _isLoggedIn.asStateFlow()

    private val _isOnboardingCompleted = MutableStateFlow(false)
    val isOnboardingCompletedState = _isOnboardingCompleted.asStateFlow()

    suspend fun getUserType() = UserType.USER.value
    suspend fun isLoggedIn() = false
    suspend fun isOnboardingCompleted() = false
    suspend fun clearLocalData() {}
}
