package com.example.kmpapp.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface UIState

sealed interface UIEvent {
    data class ShowSnackbar(val message: String) : UIEvent
    data class Navigate(val route: Any) : UIEvent
}

abstract class BaseViewModel<T : UIState>(initialUiState: T? = null) : ViewModel() {
    private val _uiState = MutableStateFlow(initialUiState)
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun updateState(update: (T) -> T) {
        _uiState.update { state ->
            state?.let { update(it) }
        }
    }

    fun onEvent(event: UIEvent) = viewModelScope.launch {
        _uiEvent.emit(event)
    }
}
