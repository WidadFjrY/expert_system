package com.tomatodisease.service

import com.tomatodisease.model.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("mappings")
    suspend fun getDiagnosisData(): MappingDataResponse

    @POST("diagnose-rule")
    suspend fun postDiagnosis(@Body request: DiagnoseRuleRequest): DiagnoseRuleResponse

    @GET("diagnose/{ruleId}")
    suspend fun getDiagnosisByRule(@Path("ruleId") ruleId: String): DiagnoseGetResponse
}

val logging = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

val client = OkHttpClient.Builder()
    .addInterceptor(logging)
    .build()

object RetrofitInstance {
    private const val BASE_URL = "http://192.168.1.5:8000/"
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
}