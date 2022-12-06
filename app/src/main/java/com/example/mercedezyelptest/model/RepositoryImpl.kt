package com.example.mercedezyelptest.model

import android.util.Log
import com.example.mercedezyelptest.model.remote.YelpApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val TAG = "RepositoryImpl"

class RepositoryImpl(private val service: YelpApi): Repository {

    override fun getBusinessByLocation(lat: Double, lon: Double): Flow<UIState> {
        return flow {
            Log.d(TAG, "getBusinessByLocation: ")
            try {
                val response = service.getBusinessByLocation(lat, lon)

                if (response.isSuccessful){
                    response.body()?.let {
                        emit(UIState.Loading(false))
                        emit(
                            UIState.Success(it.businesses)
                        )
                    } ?: emit(
                        UIState.Failure(response.message())
                    )
                }
            }catch (ex: Exception){
                ex.printStackTrace()
                emit(
                    UIState.Failure(ex.message ?: "Unknown Error")
                )
            }
        }
    }
}