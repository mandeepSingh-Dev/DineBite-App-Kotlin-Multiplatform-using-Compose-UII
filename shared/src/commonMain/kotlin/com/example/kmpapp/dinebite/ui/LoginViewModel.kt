package com.example.kmpapp.dinebite.ui

import androidx.lifecycle.viewModelScope
import com.example.kmpapp.core.base.BaseViewModel
import com.example.kmpapp.core.base.UIEvent
import com.example.kmpapp.core.base.UIState
import com.example.kmpapp.core.datastore.AppDatastore
import com.example.kmpapp.core.network.NetworkResult
import com.example.kmpapp.core.network.apis.DineBiteApi
import com.example.kmpapp.core.network.models.LoginRequestBody
import com.example.kmpapp.dinebite.navigation.DineBiteScreens
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val rememberMe: Boolean = false,
    val isLoading: Boolean = false
) : UIState

class LoginViewModel(
    private val dineBiteApi: DineBiteApi,
    private val appDatastore: AppDatastore
) : BaseViewModel<LoginUiState>(LoginUiState()) {

    fun onEmailChanged(email: String) {
        updateState { it.copy(email = email) }
    }

    fun onPasswordChanged(password: String) {
        updateState { it.copy(password = password) }
    }

    fun onRememberMeChanged(remember: Boolean) {
        updateState { it.copy(rememberMe = remember) }
    }

    fun login() {
        val currentState = uiState.value ?: return
        val email = currentState.email.trim()
        val password = currentState.password.trim()

        if (email.isEmpty()) {
            onEvent(UIEvent.ShowSnackbar("Email address cannot be empty"))
            return
        }
        if (!email.contains("@") || !email.contains(".")) {
            onEvent(UIEvent.ShowSnackbar("Please enter a valid email address"))
            return
        }
        if (password.isEmpty()) {
            onEvent(UIEvent.ShowSnackbar("Password cannot be empty"))
            return
        }

        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }
            dineBiteApi.login(LoginRequestBody(email = email, password = password))
                .collectLatest { result ->
                    updateState { it.copy(isLoading = false) }
                    when (result) {
                        is NetworkResult.Success -> {
                            val token = result.data?.data?.token
                            if (!token.isNullOrBlank()) {
                                appDatastore.saveAuthToken(token)
                            }
                            appDatastore.saveRememberMe(currentState.rememberMe)
                            DineBiteRepository.isLoggedIn = true
                            onEvent(UIEvent.Navigate(DineBiteScreens.Home))
                        }
                        is NetworkResult.Error -> {
                            println("kfnbkfnb -> ${result.error.toString()}")
                            onEvent(UIEvent.ShowSnackbar(result.error ?: "Login failed. Please check credentials."))
                        }
                        is NetworkResult.Loading -> {
                            updateState { it.copy(isLoading = true) }
                        }
                    }
                }
        }
    }
}
