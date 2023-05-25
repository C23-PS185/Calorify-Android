package com.calorify.app.data.remote.retrofit

import com.calorify.app.data.remote.request.RegisterRequest
import com.calorify.app.data.remote.response.LoginResponse
import com.calorify.app.data.remote.response.RegisterResponse
import com.google.gson.annotations.JsonAdapter
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("register")
    suspend fun register(@Body body: RegisterRequest) : RegisterResponse
}