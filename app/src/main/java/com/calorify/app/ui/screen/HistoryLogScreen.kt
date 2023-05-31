package com.calorify.app.ui.screen


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.calorify.app.ui.component.ListLog

@Composable
fun HistoryLogScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
){
    Column () {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding()
        ){
            ListLog("date", navigateToDetail = {})
        }
    }
}