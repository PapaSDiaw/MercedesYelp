package com.example.mercedezyelptest.model

import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getBusinessByLocation(lat: Double, lon: Double): Flow<UIState>
}