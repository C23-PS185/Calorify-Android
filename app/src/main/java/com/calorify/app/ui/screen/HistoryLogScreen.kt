package com.calorify.app.ui.screen


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.calorify.app.ui.component.list.ListLog
import com.calorify.app.viewmodel.ListLogViewModel

@Composable
fun HistoryLogScreen(
    month: String,
    navigateToDetail: (String) -> Unit,
    listLogViewModel: ListLogViewModel,
    modifier: Modifier = Modifier
){
    Column (modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding()
        ){
            ListLog(groupedBy = "date", listLogViewModel = listLogViewModel, navigateToDetail = navigateToDetail)
        }
    }
}