package com.calorify.app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LogKalori(
    val logId: Int,
    val title: String,
    val photo: String,
    val category: String,
    val eatTime: String,
    val portion: Int,
    val totalCalories: Int,
    val createdAtDate: String,
    val createdAtTime: String,
) : Parcelable
