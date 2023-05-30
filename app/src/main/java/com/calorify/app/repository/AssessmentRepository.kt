package com.calorify.app.repository

import androidx.lifecycle.LiveData
import com.calorify.app.helper.Result
import androidx.lifecycle.liveData
import com.calorify.app.data.remote.request.AssessmentRequest
import com.calorify.app.data.remote.response.AssessmentResponse
import com.calorify.app.data.remote.retrofit.ApiService

class AssessmentRepository(private val apiService: ApiService) {

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

    companion object {
        @Volatile
        private var instance: AssessmentRepository? = null
        fun getInstance(apiService: ApiService): AssessmentRepository =
            instance ?: synchronized(this) {
                instance ?: AssessmentRepository(apiService)
            }.also { instance = it }
    }
}