package com.tomatodisease.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diagnosis_history")
data class DiagnosisEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val ruleId: String,
    val disease: String,
    val solutions: String,
    val matchedConditions: String,
    val timestamp: Long = System.currentTimeMillis()
)
