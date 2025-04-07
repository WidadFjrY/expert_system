package com.tomatodisease.model

import com.google.gson.annotations.SerializedName

data class MappingDataResponse(
    @SerializedName("conditions")
    val condition: List<Condition>,
    @SerializedName("diseases")
    val diseases: List<Disease>,
    @SerializedName("solutions")
    val solution: List<Solution>
)

data class Condition(
    @SerializedName("id")
    val id: String,
    @SerializedName("description")
    val description: String
)

data class Disease(
    @SerializedName("id")
    val id: String,
    @SerializedName("description")
    val description: String
)

data class Solution(
    @SerializedName("id")
    val id: String,
    @SerializedName("description")
    val description: String
)

data class DiagnosisRequest(
    @SerializedName("condition_codes")
    val conditionCodes: List<String>
)