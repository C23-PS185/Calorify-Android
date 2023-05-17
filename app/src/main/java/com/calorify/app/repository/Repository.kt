package com.calorify.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.calorify.app.data.local.UserModel
import com.calorify.app.data.local.UserPreference
import com.calorify.app.data.remote.response.LoginResponse
import com.calorify.app.data.remote.response.RegisterResponse
import com.calorify.app.data.remote.retrofit.ApiConfig
import com.calorify.app.data.remote.retrofit.ApiService
import com.calorify.app.helper.Result

class Repository(private val apiService: ApiService, private val preference: UserPreference) {
    fun login(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(email, password)

            when {
                response.error -> emit(Result.Error(response.message))
                else -> {
                    val user = UserModel(
                        name = response.loginResult.name,
                        userId = response.loginResult.userId,
                        token = response.loginResult.token
                    )
                    preference.saveUser(user)
                    ApiConfig.token = response.loginResult.token
                    emit(Result.Success(response))
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun register(name: String, email: String, password: String
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(name, email, password)

            when {
                response.error -> emit(Result.Error(response.message))
                else -> emit(Result.Success(response))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(apiService: ApiService, pref: UserPreference): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, pref)
            }.also { instance = it }
    }
}