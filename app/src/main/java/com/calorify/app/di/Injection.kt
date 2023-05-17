package com.calorify.app.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.calorify.app.data.local.UserPreference
import com.calorify.app.data.remote.retrofit.ApiConfig
import com.calorify.app.repository.Repository

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        val pref = UserPreference.getInstance(context.dataStore)
        return Repository.getInstance(apiService, pref)
    }

    fun providePreferences(context: Context): UserPreference {
        return UserPreference.getInstance(context.dataStore)
    }
}