package com.tomatodisease.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomatodisease.model.MappingDataResponse
import com.tomatodisease.service.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MappingDataViewModel : ViewModel() {
    private val _data = MutableStateFlow<List<MappingDataResponse>>(emptyList())
    val data: StateFlow<List<MappingDataResponse>> = _data

    init {
        fetchDiagnosisData()
    }

    private fun fetchDiagnosisData() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getDiagnosisData()
                _data.value = listOf(response)
            } catch (e: Exception) {
                _data.value = emptyList()
            }
        }
    }
}


