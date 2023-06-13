package com.calorify.app.viewmodel

import androidx.lifecycle.ViewModel
import com.calorify.app.repository.Repository

class AssessmentResultViewModel(private val assessmentResultRepository: Repository) : ViewModel() {

    fun getUserResult(userId: String) = assessmentResultRepository.getUserResult(userId)

}