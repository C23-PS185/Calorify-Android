package com.calorify.app.data.remote.retrofit

import com.calorify.app.data.remote.request.AssessmentRequest
import com.calorify.app.data.remote.request.AssessmentUpdateRequest
import com.calorify.app.data.remote.request.CalorieLogRequest
import com.calorify.app.data.remote.response.AssessmentResponse
import com.calorify.app.data.remote.response.AssessmentResultResponse
import com.calorify.app.data.remote.response.AssessmentUpdateResponse
import com.calorify.app.data.remote.response.CalorieLogResponse
import com.calorify.app.data.remote.response.DailyCalorieResponse
import com.calorify.app.data.remote.response.MonthlyCalorieResponse
import com.calorify.app.data.remote.response.ProfileUpdateResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("add-user-data")
    suspend fun uploadAssessment(@Body body: AssessmentRequest) : AssessmentResponse

    @Headers("Content-Type: application/json")
    @POST("calorielog/{userId}")
    suspend fun uploadCalorieLog(@Path("userId") userId: String, @Body body: CalorieLogRequest) : CalorieLogResponse

    @GET("user-data/{userId}")
    suspend fun getUserResult(@Path("userId") userId: String) : AssessmentResultResponse

    @GET("monthly-calorielog/{userId}/{month}")
    suspend fun getMonthlyCalorieLog(@Path("userId") userId: String, @Path("month") month: String) : MonthlyCalorieResponse

    @Headers("Content-Type: application/json")
    @PUT("update-user-assessment/{userId}")
    suspend fun uploadUpdatedAssessment(@Body body: AssessmentUpdateRequest, @Path("userId") userId: String) : AssessmentUpdateResponse

    @Multipart
    @PUT("user-data/{userId}")
    suspend fun updateUserData(
        @Path("userId") userId: String,
        @Part("fullName") fullName: RequestBody?,
        @Part("birthDate") birthDate: RequestBody?,
        @Part("gender") gender: RequestBody?,
        @Part image: MultipartBody.Part?
    ): ProfileUpdateResponse

}
