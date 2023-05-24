package com.calorify.app.data.remote.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("passwordConfirmation")
    val passwordConfirmation: String? = null,
)