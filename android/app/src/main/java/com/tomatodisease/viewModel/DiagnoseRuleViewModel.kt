package com.tomatodisease.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomatodisease.model.DiagnoseRuleRequest
import com.tomatodisease.model.DiagnoseRuleResponse
import com.tomatodisease.service.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DiagnosisViewModel : ViewModel(){
    private val _diagnosisResponse = MutableStateFlow<DiagnoseRuleResponse?>(null)
    val diagnosisResponse: StateFlow<DiagnoseRuleResponse?> = _diagnosisResponse

    fun getRuleDiagnose(
        conditions: List<String>,
        onComplete: (DiagnoseRuleResponse?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val request = DiagnoseRuleRequest(conditions)
                val response = RetrofitInstance.api.postDiagnosis(request)
                _diagnosisResponse.value = response
                onComplete(response)
            } catch (e: Exception) {
                onComplete(null)
            }
        }
    }
}


