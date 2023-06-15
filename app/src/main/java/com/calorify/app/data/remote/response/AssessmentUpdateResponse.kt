package com.calorify.app.data.remote.response

import com.google.gson.annotations.SerializedName

data class AssessmentUpdateResponse(

    @field:SerializedName("data")
    val data: DataUser? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)
