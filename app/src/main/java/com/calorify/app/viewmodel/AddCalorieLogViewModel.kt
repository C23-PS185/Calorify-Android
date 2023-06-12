package com.calorify.app.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.calorify.app.data.remote.request.AssessmentRequest
import com.calorify.app.data.remote.request.CalorieLogRequest
import com.calorify.app.repository.Repository

class AddCalorieLogViewModel(private val addCalorieLogRepository: Repository) : ViewModel() {

    private var calorieLogData: CalorieLogRequest = CalorieLogRequest()

    fun uploadCalorieLog(userId:String) = addCalorieLogRepository.uploadCalorieLog(userId, calorieLogData)

    fun setCalorieLogData(body: CalorieLogRequest) {
        calorieLogData = body
    }
}