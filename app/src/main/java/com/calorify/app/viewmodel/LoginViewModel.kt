package com.calorify.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.calorify.app.data.remote.response.LoginResponse
import com.calorify.app.helper.Result
import com.calorify.app.repository.Repository

class LoginViewModel(private val repository: Repository) : ViewModel() {
//    fun login(email: String, password: String): LiveData<Result<LoginResponse>> {
//        return repository.login(email, password)
//    }
}