package com.calorify.app.viewmodel

import androidx.lifecycle.ViewModel
import com.calorify.app.repository.AssessmentRepository

class AssessmentResultViewModel(private val assessmentResultRepository: AssessmentRepository) : ViewModel() {

    fun getUserResult(userId: String) = assessmentResultRepository.getUserResult(userId)

}