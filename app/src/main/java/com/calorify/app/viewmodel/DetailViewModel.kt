package com.calorify.app.viewmodel

import androidx.lifecycle.ViewModel
import com.calorify.app.model.LogKalori
import com.calorify.app.repository.LogRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DetailViewModel(private val repository: LogRepository, private val logId: Int) :
    ViewModel() {
    private val _logKalori = MutableStateFlow(
        repository.getLogKaloriById(logId)
    )
    val logKalori: StateFlow<LogKalori> get() = _logKalori
}