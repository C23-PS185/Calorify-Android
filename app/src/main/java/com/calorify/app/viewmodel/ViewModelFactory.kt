package com.calorify.app.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.calorify.app.di.Injection
import com.calorify.app.repository.Repository

class ViewModelFactory private constructor(
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AssessmentViewModel::class.java) ->
                AssessmentViewModel(repository) as T

            modelClass.isAssignableFrom(AssessmentResultViewModel::class.java) ->
                AssessmentResultViewModel(repository) as T

            modelClass.isAssignableFrom(ListLogViewModel::class.java) ->
                ListLogViewModel(repository) as T

            modelClass.isAssignableFrom(AddCalorieLogViewModel::class.java) ->
                AddCalorieLogViewModel(repository) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(
                Injection.provideRepository(context),
            )
        }.also { instance = it }
    }
}