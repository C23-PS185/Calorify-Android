package com.calorify.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.calorify.app.data.remote.request.AssessmentRequest
import com.calorify.app.data.remote.response.AssessmentResponse
import com.calorify.app.data.remote.response.AssessmentResultResponse
import com.calorify.app.data.remote.response.DailyCalorieResponse
import com.calorify.app.data.remote.response.MonthlyCalorieResponse
import com.calorify.app.data.remote.retrofit.ApiService
import com.calorify.app.helper.Result

class Repository(private val apiService: ApiService) {

    fun uploadAssessment(body: AssessmentRequest): LiveData<Result<AssessmentResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.uploadAssessment(body)
            if (response.error) {
                emit(Result.Error(response.message))
            } else {
                emit(Result.Success(response))
            }
        } catch (e: Exception) {
            val message = e.message.toString()
            if (message == "") {
                emit(Result.Error("Snap, There is something wrong"))
            } else {
                emit(Result.Error(message))
            }
        }
    }

    fun getUserResult(userId: String): LiveData<Result<AssessmentResultResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUserResult(userId)
            if (response.error) {
                emit(Result.Error("Data not found"))
            } else {
                emit(Result.Success(response))
            }
        } catch (e: Exception) {
            val message = e.message.toString()
            if (message == "") {
                emit(Result.Error("Snap, There is something wrong"))
            } else {
                emit(Result.Error(message))
            }
        }
    }

    fun getMonthlyCalorie(userId: String, month: String): LiveData<Result<MonthlyCalorieResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getMonthlyCalorieLog(userId, month)
            if (response.error == true) {
                emit(Result.Error("Data not found"))
            } else {
                emit(Result.Success(response))
            }
        } catch (e: Exception) {
            val message = e.message.toString()
            if (message == "") {
                emit(Result.Error("Snap, There is something wrong"))
            } else {
                emit(Result.Error(message))
            }
        }
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(apiService: ApiService): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService)
            }.also { instance = it }
    }
}