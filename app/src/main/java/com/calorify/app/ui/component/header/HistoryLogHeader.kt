package com.calorify.app.ui.component.header

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calorify.app.R
import com.calorify.app.data.local.MonthDict
import com.calorify.app.ui.component.input.SearchBar
import com.calorify.app.ui.component.graph.LineGraph
import com.calorify.app.ui.component.input.MonthSelection
import com.calorify.app.viewmodel.ListLogViewModel

@Composable
fun HistoryLogHeader(
    isListEmpty: Boolean,
    month: String,
    monthlyCalorieFulfilled: Map<String, Int>,
    modifier: Modifier = Modifier,
    viewModel: ListLogViewModel,
    onMonthSelected: (String) -> Unit,
){
    val query by viewModel.query

    var selectedMonth by remember { mutableStateOf(month) }

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
            MonthSelection(selectedMonth) { newMonth ->
                onMonthSelected(newMonth)
                viewModel.changeMonth(newMonth)
                selectedMonth = newMonth
                Log.d("HistoryLogHeader", "selectedMonth: $selectedMonth")
            }
            if (!isListEmpty) {
                LineGraph(monthlyCalorieFulfilled)
                SearchBar(
                    query = query,
                    onQueryChange = viewModel::search,
                )
            }
        }
    }
}