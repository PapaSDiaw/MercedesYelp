package com.example.mercedezyelptest.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mercedezyelptest.model.Repository
import com.example.mercedezyelptest.model.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "BusinessViewModel"

@HiltViewModel
class BusinessViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private val _hotBusiness = MutableLiveData<UIState>()
    val hotBusiness: LiveData<UIState>
    get() = _hotBusiness

    fun getDataByLocation(lat: Double, lon: Double){
        Log.d(TAG, "getDataByLocation: ")
        viewModelScope.launch {
            repository.getBusinessByLocation(lat, lon).collect{ uiState->
                _hotBusiness.value = uiState
            }
        }
    }
}