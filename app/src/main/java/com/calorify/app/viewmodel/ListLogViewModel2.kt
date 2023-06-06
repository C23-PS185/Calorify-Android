package com.calorify.app.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.calorify.app.model.LogKalori
import com.calorify.app.repository.LogRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ListLogViewModel2(private val repository: LogRepository) : ViewModel() {
    private val _groupedEatTimeLogKalori = MutableStateFlow(
        repository.getLogKalori()
            .sortedBy { it.createdAtTime }
            .groupBy { it.eatTime }
    )
    val groupedEatTimeLogKalori: StateFlow<Map<String, List<LogKalori>>> get() = _groupedEatTimeLogKalori

    private val _groupedDateLogKalori = MutableStateFlow(
        repository.getLogKalori()
            .sortedBy { it.createdAtDate }
            .groupBy { it.createdAtDate }
    )
    val groupedDateLogKalori: StateFlow<Map<String, List<LogKalori>>> get() = _groupedDateLogKalori

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query
    fun search(newQuery: String) {
        _query.value = newQuery
        _groupedDateLogKalori.value = repository.searchLogKalori(_query.value)
            .sortedBy { it.createdAtDate }
            .groupBy { it.createdAtDate }
    }
}