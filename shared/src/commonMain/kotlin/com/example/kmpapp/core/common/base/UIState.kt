package com.example.kmpapp.core.common.base

interface UIState

open class CommonState(
    open val isLoading: Boolean = false
) : UIState
