package com.calorify.app.repository

import com.calorify.app.data.local.UserPreference
import com.calorify.app.data.remote.retrofit.ApiService

class Repository(private val apiService: ApiService, private val preference: UserPreference) {

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(apiService: ApiService, pref: UserPreference): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, pref)
            }.also { instance = it }
    }
}