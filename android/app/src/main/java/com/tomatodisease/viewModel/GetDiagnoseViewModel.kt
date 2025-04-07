package com.tomatodisease.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomatodisease.model.DiagnoseGetResponse
import com.tomatodisease.model.DiagnoseRuleRequest
import com.tomatodisease.model.DiagnoseRuleResponse
import com.tomatodisease.model.DiagnosisEntity
import com.tomatodisease.service.AppDatabase
import com.tomatodisease.service.DiagnosisDao
import com.tomatodisease.service.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GetDiagnoseViewModel : ViewModel(){
    private val _diagnoseGetResponse = MutableStateFlow<DiagnoseGetResponse?>(null)
    val diagnoseGetResponse: StateFlow<DiagnoseGetResponse?> = _diagnoseGetResponse

    fun getDiagnose(
        ruleId: String,
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getDiagnosisByRule(ruleId)
                _diagnoseGetResponse.value = response
            } catch (e: Exception) {
                Log.d("Exception", e.toString())
            }
        }
    }
}