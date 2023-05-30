package com.calorify.app.viewmodel

import androidx.lifecycle.ViewModel
import com.calorify.app.data.remote.request.AssessmentRequest
import com.calorify.app.repository.AssessmentRepository

class AssessmentViewModel(private val assessmentRepository: AssessmentRepository) : ViewModel() {
    private var assessmentData: AssessmentRequest = AssessmentRequest()

    fun uploadAssessment() = assessmentRepository.uploadAssessment(assessmentData)

    fun setAssessmentData(body: AssessmentRequest) {
        assessmentData = body
    }
}