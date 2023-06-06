package com.calorify.app.data.remote.retrofit

import com.calorify.app.data.remote.request.AssessmentRequest
import com.calorify.app.data.remote.response.AssessmentResponse
import com.calorify.app.data.remote.response.AssessmentResultResponse
import com.calorify.app.data.remote.response.DailyCalorieResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("add-user-data")
    suspend fun uploadAssessment(@Body body: AssessmentRequest) : AssessmentResponse

    @GET("user-data/{userId}")
    suspend fun getUserResult(@Path("userId") userId: String) : AssessmentResultResponse

    @GET("daily-calorielog/{userId}/{date}")
    suspend fun getDailyCalorieLog(@Path("userId") userId: String, @Path("date") date: String) : DailyCalorieResponse
}
