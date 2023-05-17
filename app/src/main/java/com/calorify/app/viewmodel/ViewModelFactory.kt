package com.calorify.app.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.calorify.app.data.local.UserPreference
import com.calorify.app.di.Injection
import com.calorify.app.repository.Repository

class ViewModelFactory private constructor(
    private val repository: Repository,
    private val preference: UserPreference
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
//            modelClass.isAssignableFrom(SplashViewModel::class.java) ->
//                SplashViewModel(preference) as T
//
//            modelClass.isAssignableFrom(LoginViewModel::class.java) ->
//                LoginViewModel(repository) as T
//
//            modelClass.isAssignableFrom(RegisterViewModel::class.java) ->
//                RegisterViewModel(repository) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(
                Injection.provideRepository(context),
                Injection.providePreferences(context)
            )
        }.also { instance = it }
    }
}