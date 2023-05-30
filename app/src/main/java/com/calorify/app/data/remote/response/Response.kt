package com.calorify.app.data.remote.response

import com.google.gson.annotations.SerializedName

data class AssessmentResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
