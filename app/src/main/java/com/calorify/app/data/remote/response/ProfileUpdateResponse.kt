package com.calorify.app.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProfileUpdateResponse(

    @field:SerializedName("data")
    val data: Profil? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class Profil(

    @field:SerializedName("photoURL")
    val photoURL: String? = null,

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("fullName")
    val fullName: String? = null,

    @field:SerializedName("birthDate")
    val birthDate: String? = null,

    @field:SerializedName("age")
    val age: Int? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)
