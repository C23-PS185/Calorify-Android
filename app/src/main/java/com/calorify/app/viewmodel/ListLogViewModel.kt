package com.calorify.app.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calorify.app.data.local.MonthDict
import com.calorify.app.data.remote.response.Data
import com.calorify.app.data.remote.response.LogItem
import com.calorify.app.data.remote.response.MonthlyCalorieResponse
import com.calorify.app.helper.Result
import com.calorify.app.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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

    fun search(newQuery: String) {
        _groupedDateLogKalori.value = _allLogKalori.value
        _query.value = newQuery
        if (_query.value != "") {
            val filteredGroupedDateLogKalori =
                _groupedDateLogKalori.value.mapValues { (_, logItems) ->
                    logItems.filter {
                        it.foodName!!.contains(
                            newQuery,
                            ignoreCase = true
                        ) or it.createdAtDate!!.contains(newQuery, ignoreCase = true)
                    }
                }
            _groupedDateLogKalori.value = filteredGroupedDateLogKalori
        }
    }

    fun changeMonth(newMonth: String) {
        Log.d("MONTH", "changeMonth: $newMonth")
        val numOfMonth = MonthDict.monthMapToNum[newMonth]
        fetchMonthlyData(
            lifecycleOwner,
            userId,
            month = numOfMonth!!,
            date = date,
            year = year,
            monthNow = monthNow
        )
    }

    private lateinit var lifecycleOwner: LifecycleOwner
    var userId = ""
    var date = ""
    var year = ""
    var monthNow = ""

    fun fetchMonthlyData(
        lifecycleOwner: LifecycleOwner,
        userId: String,
        year: String,
        month: String,
        date: String,
        monthNow: String
    ) {
        this.lifecycleOwner = lifecycleOwner
        this.userId = userId
        this.date = date
        this.year = year
        this.monthNow = monthNow
        val monthYear = "$month-$year"
        Log.d("DATE", "fetchMonthlyData: $date")

        viewModelScope.launch {
            emitLoadingState()

            try {
                val response = repository.getMonthlyCalorie(userId, monthYear)

                response.observe(lifecycleOwner) { result ->
                    result?.let { result ->
                        if (result is Result.Success) {
                            val monthlyCalorieResponse = result.data
                            if (monthlyCalorieResponse.error == true) {
                                emitErrorState("Data not found")
                            } else {
                                Log.d("DATA", "fetchMonthlyData: $monthlyCalorieResponse")
                                updateGroupedDateLogKalori(
                                    monthlyCalorieResponse,
                                    monthYear,
                                    date,
                                    month == monthNow
                                )
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
        _groupedDateLogKalori.value = emptyMap()
    }

    private fun emitErrorState(errorMessage: String) {
        // Handle the error state here as needed
    }

    private fun updateGroupedEatTimeLogKalori(data: Data) {
        val groupedEatTimeLog = mutableMapOf<String, List<LogItem>>()

        data.breakfast?.let { logItems ->
            logItems.forEach { logItem ->
                if (logItem != null) {
                    logItem.eatTime = "Sarapan"
                }
            }
            groupedEatTimeLog["Sarapan"] = logItems as List<LogItem>
        }

        data.lunch?.let { logItems ->
            logItems.forEach { logItem ->
                if (logItem != null) {
                    logItem.eatTime = "Makan Siang"
                }
            }
            groupedEatTimeLog["Makan Siang"] = logItems as List<LogItem>
        }

        data.dinner?.let { logItems ->
            logItems.forEach { logItem ->
                if (logItem != null) {
                    logItem.eatTime = "Makan Malam"
                }
            }
            groupedEatTimeLog["Makan Malam"] = logItems as List<LogItem>
        }

        data.others?.let { logItems ->
            logItems.forEach { logItem ->
                if (logItem != null) {
                    logItem.eatTime = "Lain-Lain"
                }
            }
            groupedEatTimeLog["Lain-Lain"] = logItems as List<LogItem>
        }

        _calorieFulfilled.value = data.totalDailyCalories!!
        _groupedEatTimeLogKalori.value = groupedEatTimeLog
    }

    private fun updateGroupedDateLogKalori(
        data: MonthlyCalorieResponse,
        monthYear: String,
        date: String,
        isUpdateEatTime: Boolean
    ) {

        val groupedDateLog = mutableMapOf<String, List<LogItem>>()
        val monthlyCalorie = mutableMapOf<String, Int>()

        data.monthlyLog.let { dailyCalorieList ->
            dailyCalorieList?.forEach { dailyCalorie ->
                Log.d("DATE", "isUpdate: $date, ${dailyCalorie?.date}, $isUpdateEatTime")
                if (dailyCalorie?.date == date && isUpdateEatTime) {
                    Log.d("SAMEDAY", "${dailyCalorie.data!!}")
                    updateGroupedEatTimeLogKalori(dailyCalorie.data)
                }

                val allLog = mutableListOf<LogItem>()

                dailyCalorie?.data?.breakfast?.let { logItems ->
                    logItems.forEach { logItem ->
                        if (logItem != null) {
                            logItem.eatTime = "Sarapan"
                        }
                    }
                    allLog.addAll(logItems as List<LogItem>)
                }

                dailyCalorie?.data?.lunch?.let { logItems ->
                    logItems.forEach { logItem ->
                        if (logItem != null) {
                            logItem.eatTime = "Makan Siang"
                        }
                    }
                    allLog.addAll(logItems as List<LogItem>)
                }

                dailyCalorie?.data?.dinner?.let { logItems ->
                    logItems.forEach { logItem ->
                        if (logItem != null) {
                            logItem.eatTime = "Makan Malam"
                        }
                    }
                    allLog.addAll(logItems as List<LogItem>)
                }

                dailyCalorie?.data?.others?.let { logItems ->
                    logItems.forEach { logItem ->
                        if (logItem != null) {
                            logItem.eatTime = "Lain-Lain"
                        }
                    }
                    allLog.addAll(logItems as List<LogItem>)
                }
                groupedDateLog["${dailyCalorie?.date}-${monthYear}"] = allLog
                monthlyCalorie[dailyCalorie?.date!!] = dailyCalorie.data?.totalDailyCalories!!
            }
        }

        _monthlyCalorieFullfiled.value = monthlyCalorie
        _groupedDateLogKalori.value = groupedDateLog
        _allLogKalori.value = groupedDateLog
    }

    fun getLogItemById(logItemId: String): LogItem {
        val allLogItems = mutableListOf<LogItem>()

        _groupedDateLogKalori.value.forEach { (_, logItems) ->
            allLogItems.addAll(logItems)
        }

        return allLogItems.find { it.logId == logItemId } ?: getLogItemByIdInHome(logItemId)
    }

    fun getLogItemByIdInHome(logItemId: String): LogItem {
        val allLogItems = mutableListOf<LogItem>()

        _groupedEatTimeLogKalori.value.forEach { (_, logItems) ->
            allLogItems.addAll(logItems)
        }

        return allLogItems.find { it.logId == logItemId }!!
    }
}