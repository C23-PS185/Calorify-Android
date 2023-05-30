package com.calorify.app.repository

import android.content.Context
import android.content.res.Resources
import com.calorify.app.R
import com.calorify.app.model.LogKalori


class LogRepository(private val context: Context) {
    private val resources: Resources = context.resources
    var listLogKalori: List<LogKalori>

    init {
        val dataId = resources.getStringArray(R.array.data_id)
        val dataTitle = resources.getStringArray(R.array.data_title)
        val dataPhoto = resources.getStringArray(R.array.data_photo)
        val dataCategory = resources.getStringArray(R.array.data_category)
        val dataEatTime = resources.getStringArray(R.array.data_eat_time)
        val dataPortion = resources.getStringArray(R.array.data_portion)
        val dataTotalCalories = resources.getStringArray(R.array.data_total_calories)
        val dataCreatedAtDate = resources.getStringArray(R.array.data_created_at_date)
        val dataCreatedAtTime = resources.getStringArray(R.array.data_created_at_time)
        val listLogKalori = ArrayList<LogKalori>()
        for (i in dataTitle.indices) {
            val recipe = LogKalori(dataId[i].toInt(), dataTitle[i], dataPhoto[i], dataCategory[i], dataEatTime[i], dataPortion[i].toInt(), dataTotalCalories[i].toInt(), dataCreatedAtDate[i], dataCreatedAtTime[i])
            listLogKalori.add(recipe)
        }
        this.listLogKalori = listLogKalori
    }

    fun getLogKalori(): List<LogKalori> {
        return this.listLogKalori
    }

    fun searchLogKalori(query: String): List<LogKalori>{
        return getLogKalori().filter {
            it.title.contains(query, ignoreCase = true)
        }
    }

    fun getLogKaloriById(id: Int): LogKalori {
        return listLogKalori.first {
            it.logId == id
        }
    }
}