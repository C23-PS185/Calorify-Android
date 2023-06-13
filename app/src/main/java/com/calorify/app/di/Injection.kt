package com.calorify.app.di

import android.content.Context
import com.calorify.app.data.remote.retrofit.ApiConfig
import com.calorify.app.repository.Repository


object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(apiService)
    }
}