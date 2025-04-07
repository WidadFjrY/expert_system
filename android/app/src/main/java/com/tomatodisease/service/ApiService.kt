package com.tomatodisease.service

import com.tomatodisease.model.DiagnosisResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface DiagnosisApiService {
    @GET("mappings/")
    suspend fun getDiagnosisData(): DiagnosisResponse
}

object RetrofitInstance {
    private const val BASE_URL = "http://192.168.1.5:8000/"

    val apiService: DiagnosisApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DiagnosisApiService::class.java)
    }
}