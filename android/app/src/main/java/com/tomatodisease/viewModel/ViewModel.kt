package com.tomatodisease.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomatodisease.model.DiagnosisResponse
import com.tomatodisease.service.RetrofitInstance
import kotlinx.coroutines.launch

class DiagnosisViewModel: ViewModel(){
    private var diagnosisData by mutableStateOf<DiagnosisResponse?>(null)
        private set

    private var isLoading by mutableStateOf(false)
    private var error by mutableStateOf<String?>(null)

    fun fetchDiagnosisData(){
        viewModelScope.launch {
            isLoading = true
            error = null

            try {
                val response = RetrofitInstance.apiService.getDiagnosisData()
                diagnosisData = response
            } catch (e: Exception) {
                error = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }
}
