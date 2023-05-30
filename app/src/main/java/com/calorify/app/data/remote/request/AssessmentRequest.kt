package com.calorify.app.data.remote.request

import com.google.gson.annotations.SerializedName

data class AssessmentRequest(
    @field:SerializedName("userId")
    val userId: String? = null,

    @field:SerializedName("fullName")
    val fullName: String? = null,

    @field:SerializedName("birthDate")
    val birthDate: String? = null,

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("userHeight")
    val userHeight: String? = null,

    @field:SerializedName("userWeight")
    val userWeight: String? = null,

    @field:SerializedName("activityLevel")
    val activityLevel: Int? = null,

    @field:SerializedName("stressLevel")
    val stressLevel: Int? = null,

    @field:SerializedName("weightGoal")
    val weightGoal: Int? = null,
)