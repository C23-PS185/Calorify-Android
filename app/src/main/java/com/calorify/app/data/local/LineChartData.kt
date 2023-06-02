package com.calorify.app.data.local

data class LineChartData(
    var date: String,
    var value: Float?
)

val getLineChartData = listOf(
    LineChartData("10", 1600F),
    LineChartData("11", 1500F),
    LineChartData("12", 1800F),
    LineChartData("13", 1300F),
    LineChartData("14", 1500F),
    LineChartData("15", 1600F),
    LineChartData("16", 1750F),
    LineChartData("17", 1400F),
    LineChartData("18", 1560F),
    LineChartData("19", 1300F),
    LineChartData("20", 1280F),
    LineChartData("21", 1700F),
)
