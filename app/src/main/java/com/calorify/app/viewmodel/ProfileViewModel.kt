package com.calorify.app.viewmodel

import androidx.lifecycle.ViewModel
import com.calorify.app.data.remote.request.AssessmentUpdateRequest
import com.calorify.app.repository.Repository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ProfileViewModel(private val repository: Repository) : ViewModel() {

    fun uploadUpdateAssessment(userId: String, body: AssessmentUpdateRequest) =
        repository.updateAssessment(userId, body)

    fun uploadUpdateData(
        userId: String,
        fullname: RequestBody,
        birthDate: RequestBody,
        gender: RequestBody,
        image: MultipartBody.Part?
    ) = repository.updateProfile(userId, fullname, birthDate, gender, image)

}