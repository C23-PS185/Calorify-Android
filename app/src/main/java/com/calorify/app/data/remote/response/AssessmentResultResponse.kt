package com.calorify.app.data.remote.response

import com.google.gson.annotations.SerializedName

data class AssessmentResultResponse(

    @field:SerializedName("data")
    val data: DataUser,

    @field:SerializedName("error")
    val error: Boolean
)

data class DataUser(

    @field:SerializedName("userWeight")
    val userWeight: String? = null,

    @field:SerializedName("userHeight")
    val userHeight: String? = null,

    @field:SerializedName("gender")
    var gender: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("userCalorieIntake")
    val userCalorieIntake: Int? = null,

    @field:SerializedName("weightGoal")
    val weightGoal: Int? = null,

    @field:SerializedName("userBMI")
    val userBMI: Float? = null,

    @field:SerializedName("birthDate")
    var birthDate: String? = null,

    @field:SerializedName("fullName")
    var fullName: String? = null,

    @field:SerializedName("photoURL")
    var photoURL: String? = null,

    @field:SerializedName("age")
    var age: Int? = null
)