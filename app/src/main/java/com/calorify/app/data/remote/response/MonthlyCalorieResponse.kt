package com.calorify.app.data.remote.response

import com.google.gson.annotations.SerializedName

data class MonthlyCalorieResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("monthlyLog")
	val monthlyLog: List<DailyCalorie?>? = null,

	@field:SerializedName("totalMonthlyCalories")
	val totalMonthlyCalories: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DailyCalorie(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("id")
	val date: String? = null
)

