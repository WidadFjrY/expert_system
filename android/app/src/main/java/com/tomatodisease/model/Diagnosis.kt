package com.tomatodisease.model

import android.os.Parcelable
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

data class DiagnoseRuleRequest(
    @SerializedName("condition_codes")
    val conditionCodes: List<String>
)

data class DiagnoseRuleResponse(
    @SerializedName("rule_id")
    val ruleId: String
)

data class DiagnoseGetResponse(
    @SerializedName("rule_id")
    val ruleId: String,
    @SerializedName("disease")
    val disease: String,
    @SerializedName("solutions")
    val solution: List<String>,
    @SerializedName("matched_conditions")
    val matchedConditions: List<String>
)