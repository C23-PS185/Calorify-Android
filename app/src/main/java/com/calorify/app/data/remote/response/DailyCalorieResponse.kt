package com.calorify.app.data.remote.response

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class DailyCalorieResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("error")
	val error: Boolean? = null
)

data class LogItem(

	val logId: String = UUID.randomUUID().toString(),

	@field:SerializedName("createdAtDate")
	val createdAtDate: String? = null,

	@field:SerializedName("createdAtTime")
	val createdAtTime: String? = null,

	@field:SerializedName("foodName")
	val foodName: String? = null,

	@field:SerializedName("foodCalories")
	val foodCalories: Int? = null,

	@field:SerializedName("fnbType")
	val fnbType: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = "https://www.freeiconspng.com/thumbs/no-image-icon/no-image-icon-6.png",

	@field:SerializedName("eatTime")
	var eatTime: String? = null
)

data class Data(

	@field:SerializedName("totalDailyCalories")
	val totalDailyCalories: Int? = null,

	@field:SerializedName("dinner")
	val dinner: List<LogItem?>? = null,

	@field:SerializedName("lunch")
	val lunch: List<LogItem?>? = null,

	@field:SerializedName("breakfast")
	val breakfast: List<LogItem?>? = null,

	@field:SerializedName("others")
	val others: List<LogItem?>? = null,
)
