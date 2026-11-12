package com.example.kmpapp.core.common.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.kmpapp.core.common.base.UIEvent
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ObserveUIEvents(uiEvent: SharedFlow<UIEvent>, onEvent : suspend (UIEvent?) -> Unit) {
    LaunchedEffect(uiEvent) {
        uiEvent.collectLatest {
            onEvent(it)
        }
    }
}
