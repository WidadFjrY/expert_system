package com.tomatodisease.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomatodisease.model.DiagnoseRuleRequest
import com.tomatodisease.model.DiagnoseRuleResponse
import com.tomatodisease.model.DiagnosisEntity
import com.tomatodisease.service.AppDatabase
import com.tomatodisease.service.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DiagnoseRuleViewModel : ViewModel(){
    fun getRuleDiagnose(
        conditions: List<String>,
        onComplete: (DiagnoseRuleResponse?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val request = DiagnoseRuleRequest(conditions)
                val response = RetrofitInstance.api.postDiagnosis(request)
                onComplete(response)
            } catch (e: Exception) {
                onComplete(null)
            }
        }
    }
}
