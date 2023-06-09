package com.calorify.app.ui.component.header

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.calorify.app.R
import com.calorify.app.repository.LogRepository
import com.calorify.app.ui.component.bar.SearchBar
import com.calorify.app.ui.component.graph.LineGraph
import com.calorify.app.ui.component.input.MonthSelection
import com.calorify.app.viewmodel.ListLogViewModel

@Composable
fun HistoryLogHeader(
    monthlyCalorieFulfilled: Map<String, Int>,
    modifier: Modifier = Modifier,
    viewModel: ListLogViewModel,
){
    val query by viewModel.query

    Column () {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ){
            Text(
                text = "Riwayat Log",
                fontFamily = FontFamily(
                    Font(resId = R.font.inter_bold),
                ),
                fontSize = 24.sp,
                modifier = Modifier.padding(8.dp)
            )
            MonthSelection()
            LineGraph(monthlyCalorieFulfilled)
            SearchBar(
                query = query,
                onQueryChange = viewModel::search,
            )
        }
    }
}