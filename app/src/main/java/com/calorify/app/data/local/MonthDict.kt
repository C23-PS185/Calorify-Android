package com.calorify.app.data.local

object MonthDict {
    val numMapToMonth = mapOf(
        "01" to "Januari",
        "02" to "Februari",
        "03" to "Maret",
        "04" to "April",
        "05" to "Mei",
        "06" to "Juni",
        "07" to "Juli",
        "08" to "Agustus",
        "09" to "September",
        "10" to "Oktober",
        "11" to "November",
        "12" to "Desember"
    )

    val monthMapToNum: Map<String, String> =
        numMapToMonth.entries.associate { (key, value) -> value to key }

}