package com.calorify.app.data.remote.retrofit

import com.calorify.app.data.remote.request.AssessmentRequest
import com.calorify.app.data.remote.response.AssessmentResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("add-user-data")
    suspend fun uploadAssessment(@Body body: AssessmentRequest) : AssessmentResponse
}