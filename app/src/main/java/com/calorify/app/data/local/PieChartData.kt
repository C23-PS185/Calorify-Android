package com.calorify.app.data.local

// on below line we are creating data class for
// pie chart data and passing variable as browser
// name and value.
data class PieChartData(
    var status: String?,
    var value: Float?
)

// on below line we are creating a method
// in which we are passing all the data.
val getPieChartData = listOf(
    PieChartData("Terpenuhi", 756F),
    PieChartData("Dibutuhkan", 532F),
)
