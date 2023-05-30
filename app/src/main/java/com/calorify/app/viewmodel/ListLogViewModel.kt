package com.calorify.app.viewmodel

import androidx.lifecycle.ViewModel
import com.calorify.app.model.LogKalori
import com.calorify.app.repository.LogRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ListLogViewModel(private val repository: LogRepository) : ViewModel() {
    private val _groupedLogKalori = MutableStateFlow(
        repository.getLogKalori()
            .groupBy { it.eatTime }
    )
    val groupedLogKalori: StateFlow<Map<String, List<LogKalori>>> get() = _groupedLogKalori
}