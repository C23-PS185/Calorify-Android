package com.calorify.app.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calorify.app.data.remote.response.DailyCalorieResponse
import com.calorify.app.data.remote.response.LogItem
import com.calorify.app.data.remote.response.MonthlyCalorieResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.calorify.app.helper.Result
import com.calorify.app.repository.Repository
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.forEach

class ListLogViewModel(private val repository: Repository) : ViewModel() {
    private val _groupedEatTimeLogKalori = MutableStateFlow<Map<String, List<LogItem>>>(emptyMap())
    val groupedEatTimeLogKalori: StateFlow<Map<String, List<LogItem>>> get() = _groupedEatTimeLogKalori

    private val _groupedDateLogKalori = MutableStateFlow<Map<String, List<LogItem>>>(emptyMap())
    val groupedDateLogKalori: StateFlow<Map<String, List<LogItem>>> get() = _groupedDateLogKalori

    private val _allLogKalori = MutableStateFlow<Map<String, List<LogItem>>>(emptyMap())

    private val _calorieFulfilled = MutableStateFlow<Int>(0)
    val calorieFulfilled: StateFlow<Int> get() = _calorieFulfilled

    private val _monthlyCalorieFullfiled = MutableStateFlow<Map<String, Int>>(emptyMap())
    val monthlyCalorieFullfiled: StateFlow<Map<String, Int>> get() = _monthlyCalorieFullfiled

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    var dummyId = "YgblzDZfAUQlukFlGxsdCtt2jKE3"
    var dummyMonth = "06-2023"
    var dummyDate = "07-06-2023"

    fun search(newQuery: String) {
        _groupedDateLogKalori.value = _allLogKalori.value
        _query.value = newQuery
        if(_query.value != ""){
            val filteredGroupedDateLogKalori = _groupedDateLogKalori.value.mapValues { (_, logItems) ->
                logItems.filter {
                    it.foodName!!.contains(newQuery, ignoreCase = true) or it.createdAtDate!!.contains(newQuery, ignoreCase = true)
                }
            }
            _groupedDateLogKalori.value = filteredGroupedDateLogKalori
        }
    }

    fun fetchDailyData(lifecycleOwner: LifecycleOwner, userId: String, date: String, callback: () -> Unit) {
        viewModelScope.launch {
            emitLoadingState()

            try {
                val response = repository.getDailyCalorie(userId, date)

                response.observe(lifecycleOwner) { result ->
                    result?.let { result ->
                        if (result is Result.Success) {
                            val dailyCalorieResponse = result.data
                            if (dailyCalorieResponse.error == true) {
                                emitErrorState("Data not found")
                            } else {
                                _calorieFulfilled.value = dailyCalorieResponse.data?.totalDailyCalories!!
                                updateGroupedEatTimeLogKalori(dailyCalorieResponse)
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
            } finally {
                callback()
            }
        }
    }

    fun fetchMonthlyData(lifecycleOwner: LifecycleOwner, userId: String, month: String, date: String) {
        viewModelScope.launch {
            emitLoadingState()

            try {
                val response = repository.getMonthlyCalorie(userId, month)

                response.observe(lifecycleOwner) { result ->
                    result?.let { result ->
                        if (result is Result.Success) {
                            val monthlyCalorieResponse = result.data
                            if (monthlyCalorieResponse.error == true) {
                                emitErrorState("Data not found")
                            } else {
                                Log.d("DATA", "fetchMonthlyData: $monthlyCalorieResponse")
                                updateGroupedDateLogKalori(monthlyCalorieResponse, month, date)
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

    private fun updateGroupedDateLogKalori(data: MonthlyCalorieResponse, month: String, date: String) {

        val groupedDateLog = mutableMapOf<String, List<LogItem>>()
        val monthlyCalorie = mutableMapOf<String, Int>()

        data.monthlyLog.let {dailyCalorieList ->
            dailyCalorieList?.forEach {dailyCalorie ->
                if (dailyCalorie?.date == date){
                    val thisDayLogItems = mutableListOf<LogItem>()

                    if (_groupedEatTimeLogKalori.value.isNullOrEmpty()){
                        updateGroupedDateLogKalori(data, month, date)
                        return
                    } else {
                        _groupedEatTimeLogKalori.value.forEach { (_, logItems) ->
                            thisDayLogItems.addAll(logItems)
                        }
                    }

                    groupedDateLog["$date-$month"] = thisDayLogItems
                    monthlyCalorie[date] = dailyCalorie.data?.totalDailyCalories!!
                } else {
                    val allLog = mutableListOf<LogItem>()

                    dailyCalorie?.data?.breakfast?.let { logItems ->
                        logItems?.forEach { logItem ->
                            if (logItem != null) {
                                logItem.eatTime = "Sarapan"
                            }
                        }
                        allLog.addAll(logItems as List<LogItem>)
                    }

                    dailyCalorie?.data?.lunch?.let { logItems ->
                        logItems?.forEach { logItem ->
                            if (logItem != null) {
                                logItem.eatTime = "Makan Siang"
                            }
                        }
                        allLog.addAll(logItems as List<LogItem>)
                    }

                    dailyCalorie?.data?.dinner?.let { logItems ->
                        logItems?.forEach { logItem ->
                            if (logItem != null) {
                                logItem.eatTime = "Makan Malam"
                            }
                        }
                        allLog.addAll(logItems as List<LogItem>)
                    }

                    dailyCalorie?.data?.others?.let { logItems ->
                        logItems?.forEach { logItem ->
                            if (logItem != null) {
                                logItem.eatTime = "Lain-Lain"
                            }
                        }
                        allLog.addAll(logItems as List<LogItem>)
                    }
                    groupedDateLog["${dailyCalorie?.date}-${month}"] = allLog
                    monthlyCalorie[dailyCalorie?.date!!] = dailyCalorie.data?.totalDailyCalories!!
                }
            }
        }

        _monthlyCalorieFullfiled.value = monthlyCalorie
        _groupedDateLogKalori.value = groupedDateLog
        _allLogKalori.value = groupedDateLog
    }

    fun getLogItemById(logItemId: String): LogItem? {
        val allLogItems = mutableListOf<LogItem>()

        _groupedDateLogKalori.value.forEach { (_, logItems) ->
            allLogItems.addAll(logItems)
        }

        return allLogItems.find { it.logId == logItemId }
    }
}