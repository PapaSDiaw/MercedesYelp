package com.example.mercedezyelptest.model

import com.example.mercedezyelptest.model.remote.Business

sealed class UIState{
    data class Success(val data: List<Business>): UIState()
    data class Failure(val errorMessage: String): UIState()
    data class Loading(val isLoading: Boolean = true): UIState()
}
