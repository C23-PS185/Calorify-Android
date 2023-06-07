package com.calorify.app.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calorify.app.data.remote.response.DailyCalorieResponse
import com.calorify.app.data.remote.response.LogItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.calorify.app.helper.Result
import com.calorify.app.model.LogKalori
import com.calorify.app.repository.Repository

class ListLogViewModel(private val repository: Repository) : ViewModel() {
    private val _groupedEatTimeLogKalori = MutableStateFlow<Map<String, List<LogItem>>>(emptyMap())
    val groupedEatTimeLogKalori: StateFlow<Map<String, List<LogItem>>> get() = _groupedEatTimeLogKalori

    private val _groupedDateLogKalori = MutableStateFlow<Map<String, List<LogItem>>>(emptyMap())
    val groupedDateLogKalori: StateFlow<Map<String, List<LogItem>>> get() = _groupedDateLogKalori

    private val _calorieFulfilled = MutableStateFlow<Float>(0f)
    val calorieFulfilled: StateFlow<Float> get() = _calorieFulfilled

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

//    fun search(newQuery: String) {
//        _query.value = newQuery
//        updateGroupedDateLogKalori(repository.searchLogKalori(_query.value))
//    }

    fun fetchData(userId: String, date: String) {
        viewModelScope.launch {
            emitLoadingState()

            try {
                val dummyUserId = "YgblzDZfAUQlukFlGxsdCtt2jKE3"
                val dummyDate = "06-06-2023"
                val response = repository.getDailyCalorie(userId, date)

                response.observeForever { result ->
                    result?.let { result ->
                        if (result is Result.Success) {
                            val dailyCalorieResponse = result.data
                            if (dailyCalorieResponse.error == true) {
                                emitErrorState("Data not found")
                            } else {
                                _calorieFulfilled.value = dailyCalorieResponse.data?.totalCalories!!
                                updateGroupedEatTimeLogKalori(dailyCalorieResponse)
                                updateGroupedDateLogKalori(dailyCalorieResponse)
                            }
                        } else if (result is Result.Error) {
                            emitErrorState(
                                result.error
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                val message = e.message.toString()
                val errorMessage = if (message.isEmpty()) {
                    "Snap, There is something wrong"
                } else {
                    message
                }
                emitErrorState(errorMessage)
            }
        }
    }

    private fun emitLoadingState() {
        _groupedEatTimeLogKalori.value = emptyMap()
        _groupedDateLogKalori.value = emptyMap()
    }

    private fun emitErrorState(errorMessage: String) {
        // Handle the error state here as needed
    }

    private fun updateGroupedEatTimeLogKalori(data: DailyCalorieResponse) {
        val groupedEatTimeLog = mutableMapOf<String, List<LogItem>>()

        data.data?.breakfast?.let { logItems ->
            logItems.forEach { logItem ->
                if (logItem != null) {
                    logItem.eatTime = "Sarapan"
                }
            }
            groupedEatTimeLog["Sarapan"] = logItems as List<LogItem>
        }

        data.data?.lunch?.let { logItems ->
            logItems.forEach { logItem ->
                if (logItem != null) {
                    logItem.eatTime = "Makan Siang"
                }
            }
            groupedEatTimeLog["Makan Siang"] = logItems as List<LogItem>
        }

        data.data?.dinner?.let { logItems ->
            logItems.forEach { logItem ->
                if (logItem != null) {
                    logItem.eatTime = "Makan Malam"
                }
            }
            groupedEatTimeLog["Makan Malam"] = logItems as List<LogItem>
        }

        Log.d("TES", "updateGroupedEatTimeLogKalori: ${groupedEatTimeLog["Makan Malam"]!!.forEach { logItem -> logItem.foodName }}")

        data.data?.others?.let { logItems ->
            logItems.forEach { logItem ->
                if (logItem != null) {
                    logItem.eatTime = "Lain-Lain"
                }
            }
            groupedEatTimeLog["Lain-Lain"] = logItems as List<LogItem>
        }

        _groupedEatTimeLogKalori.value = groupedEatTimeLog
    }

    private fun updateGroupedDateLogKalori(data: DailyCalorieResponse) {

        val allLog = mutableListOf<LogItem>()

        data.data?.breakfast?.let { logItems ->
            allLog.addAll(logItems as List<LogItem>)
        }
        data.data?.lunch?.let { logItems ->
            allLog.addAll(logItems as List<LogItem>)
        }
        data.data?.dinner?.let { logItems ->
            allLog.addAll(logItems as List<LogItem>)
        }
        data.data?.others?.let { logItems ->
            allLog.addAll(logItems as List<LogItem>)
        }

        // Group the log by createdAtDate
        val groupedDateLog = allLog
            .groupBy { it.createdAtDate!! }

        _groupedDateLogKalori.value = groupedDateLog
    }

    fun getLogItemById(logItemId: String): LogItem? {
        val allLogItems = mutableListOf<LogItem>()

        _groupedDateLogKalori.value.forEach { (_, logItems) ->
            allLogItems.addAll(logItems)
        }

        Log.d("TES", "getLogItemById: ${allLogItems.find { it.logId == logItemId }}")

        return allLogItems.find { it.logId == logItemId }
    }
}