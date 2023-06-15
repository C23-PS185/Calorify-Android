package com.calorify.app.ui.component.header

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.calorify.app.ui.component.graph.PieChart

@Composable
fun HomeHeader(calorieNeeded: Int, calorieFulfilled: Int) {
    Column() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            PieChart(calorieNeeded, calorieFulfilled)
        }
    }
}