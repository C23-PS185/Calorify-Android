package com.calorify.app.data.local

data class PieChartData(
    var status: String?,
    var value: Float?
)

val getPieChartData = listOf(
    PieChartData("Terpenuhi", 756F),
    PieChartData("Dibutuhkan", 532F),
)
