package com.calorify.app.data.remote.request

import com.google.gson.annotations.SerializedName

data class AssessmentUpdateRequest(

    @field:SerializedName("userWeight")
    val userWeight: Int? = null,

    @field:SerializedName("weightGoal")
    val weightGoal: Int? = null,

    @field:SerializedName("userHeight")
    val userHeight: Int? = null,

    @field:SerializedName("stressLevel")
    val stressLevel: Int? = null,

    @field:SerializedName("activityLevel")
    val activityLevel: Int? = null
)
