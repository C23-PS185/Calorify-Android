package com.calorify.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.calorify.app.data.remote.request.RegisterRequest
import com.calorify.app.data.remote.response.RegisterResponse
import com.calorify.app.repository.Repository
import com.calorify.app.helper.Result

class RegisterViewModel(private val repository: Repository) : ViewModel() {
    private var registerInfo: RegisterRequest = RegisterRequest()

    fun register() = repository.register(registerInfo)

    fun setRegisterInfo(body: RegisterRequest) {
        registerInfo = body
    }

}