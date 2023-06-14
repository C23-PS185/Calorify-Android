package com.calorify.app.data.remote.request

import com.google.gson.annotations.SerializedName

data class CalorieLogRequest(

    @field:SerializedName("foodName")
    val foodName: String? = null,

    @field:SerializedName("fnbType")
    val fnbType: String? = null,

    @field:SerializedName("foodCalories")
    val foodCalories: Int? = null,

    @field:SerializedName("mealTime")
    val mealTime: Int? = null
)
