package com.example.kmpapp.dinebite.ui

import androidx.lifecycle.viewModelScope
import com.example.kmpapp.core.base.BaseViewModel
import com.example.kmpapp.core.base.UIEvent
import com.example.kmpapp.core.base.UIState
import com.example.kmpapp.core.datastore.AppDatastore
import com.example.kmpapp.core.network.NetworkResult
import com.example.kmpapp.core.network.apis.DineBiteApi
import com.example.kmpapp.core.network.models.RegisterRequestBody
import com.example.kmpapp.dinebite.navigation.DineBiteScreens
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class SignupUiState(
    val fullName: String = "",
    val mobile: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false
) : UIState

class SignupViewModel(
    private val dineBiteApi: DineBiteApi,
    private val appDatastore: AppDatastore
) : BaseViewModel<SignupUiState>(SignupUiState()) {

    fun onFullNameChanged(name: String) {
        updateState { it.copy(fullName = name) }
    }

    fun onMobileChanged(mobile: String) {
        updateState { it.copy(mobile = mobile) }
    }

    fun onEmailChanged(email: String) {
        updateState { it.copy(email = email) }
    }

    fun onPasswordChanged(password: String) {
        updateState { it.copy(password = password) }
    }

    fun signup() {
        val currentState = uiState.value ?: return
        val name = currentState.fullName.trim()
        val mobile = currentState.mobile.trim()
        val email = currentState.email.trim()
        val password = currentState.password.trim()

        if (name.isEmpty()) {
            onEvent(UIEvent.ShowSnackbar("Full name cannot be empty"))
            return
        }
        if (mobile.isEmpty() || mobile.length < 10) {
            onEvent(UIEvent.ShowSnackbar("Please enter a valid mobile number"))
            return
        }
        if (email.isEmpty() || !email.contains("@") || !email.contains(".")) {
            onEvent(UIEvent.ShowSnackbar("Please enter a valid email address"))
            return
        }

        val passwordRegex = Regex("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#\$%^&+=!_]).{8,}$")
        if (!password.matches(passwordRegex)) {
            onEvent(UIEvent.ShowSnackbar("Password must be 8+ chars with 1 capital letter, 1 number, and 1 special character"))
            return
        }

        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }
            dineBiteApi.register(RegisterRequestBody(name = name, email = email, phone = mobile, password = password))
                .collectLatest { result ->
                    updateState { it.copy(isLoading = false) }
                    when (result) {
                        is NetworkResult.Success -> {
                            val token = result.data?.data?.token
                            if (!token.isNullOrBlank()) {
                                appDatastore.saveAuthToken(token)
                            }
                            DineBiteRepository.isLoggedIn = true
                            DineBiteRepository.userProfile.name = name
                            DineBiteRepository.userProfile.email = email
                            DineBiteRepository.userProfile.phone = mobile
                            onEvent(UIEvent.Navigate(DineBiteScreens.Home))
                        }
                        is NetworkResult.Error -> {
                            onEvent(UIEvent.ShowSnackbar(result.error ?: "Registration failed. Please try again."))
                        }
                        is NetworkResult.Loading -> {
                            updateState { it.copy(isLoading = true) }
                        }
                    }
                }
        }
    }
}
