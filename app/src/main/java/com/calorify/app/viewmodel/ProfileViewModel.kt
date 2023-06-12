package com.calorify.app.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calorify.app.data.local.MonthDict
import com.calorify.app.data.remote.request.AssessmentUpdateRequest
import com.calorify.app.data.remote.response.DailyCalorieResponse
import com.calorify.app.data.remote.response.Data
import com.calorify.app.data.remote.response.DataUser
import com.calorify.app.data.remote.response.LogItem
import com.calorify.app.data.remote.response.MonthlyCalorieResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.calorify.app.helper.Result
import com.calorify.app.repository.Repository
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.forEach
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ProfileViewModel(private val repository: Repository) : ViewModel() {

    fun uploadUpdateAssessment(userId: String, body: AssessmentUpdateRequest) = repository.updateAssessment(userId, body)

    fun uploadUpdateData(userId: String, fullname: RequestBody, birthDate: RequestBody, gender: RequestBody, image: MultipartBody.Part?) = repository.updateProfile(userId, fullname, birthDate, gender, image)

}