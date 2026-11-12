package com.example.kmpapp.feature.splash.ui

import androidx.lifecycle.viewModelScope
import com.example.kmpapp.core.common.UserManager
import com.example.kmpapp.core.common.base.BaseViewModel
import com.example.kmpapp.core.common.base.UIState
import com.example.kmpapp.core.common.utils.CommonUIEvents
import com.example.kmpapp.core.common.utils.Constants
import com.example.kmpapp.core.common.utils.enums.UserType
import com.example.kmpapp.core.navigation.AppScreens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class SplashViewModel(
    private val userManager: UserManager
) : BaseViewModel<UIState>() {

    var splashJob : Job? = null
    fun startTimer(){
        splashJob?.cancel()
        splashJob = viewModelScope.launch(Dispatchers.Default) {
            delay(Constants.SPLASH_DELAY.milliseconds)
            checkNavigation()
        }
    }

    suspend fun checkNavigation(){
        if(userManager.isLoggedIn()){
            if(userManager.getUserType() == UserType.USER.value){
                onEvent(CommonUIEvents.NavigateTo(AppScreens.HomeBottomContainerScreen))
            }else{
                onEvent(CommonUIEvents.NavigateTo(AppScreens.HomeBottomContainerHostScreen))
            }
        }else{
            if(userManager.isOnboardingCompleted()){
                onEvent(CommonUIEvents.NavigateTo(AppScreens.UserTypeSelectionScreen))
            }else{
                onEvent(CommonUIEvents.NavigateTo(AppScreens.OnBoardingScreen))
            }
        }
    }
}
