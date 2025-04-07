package com.tomatodisease.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tomatodisease.model.DiagnosisEntity
import com.tomatodisease.service.AppDatabase
import com.tomatodisease.service.DiagnosisDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).diagnosisDao()

    val history: Flow<List<DiagnosisEntity>> = dao.getAllHistory()

    fun deleteAllHistory() {
        viewModelScope.launch {
            dao.deleteAll()
        }
    }

    fun saveDiagnosisToLocal(entity: DiagnosisEntity, dao: DiagnosisDao){
        viewModelScope.launch {
            dao.insertDiagnosis(entity)
        }
    }
}
