package com.calorify.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.calorify.app.repository.LogRepository


class ViewModelFactory2(private val repository: LogRepository, private val recipeTitle: String = "") :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListLogViewModel::class.java)) {
            return ListLogViewModel(repository) as T
        }
//        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
//            return DetailViewModel(repository, recipeTitle) as T
//        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}