package com.calorify.app.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.calorify.app.ui.component.header.HomeStickyHeader
import com.calorify.app.ui.component.list.ListLog
import com.calorify.app.viewmodel.ListLogViewModel

@Composable
fun HomeScreen(
    firstName: String,
    photoURL: String,
    calorieNeeded: Int,
    listLogViewModel: ListLogViewModel,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier){
    Column (modifier = modifier) {
        HomeStickyHeader(name = firstName, photo = photoURL)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding()
        ){
            ListLog(calorieNeeded = calorieNeeded, groupedBy =  "eat time", navigateToDetail = navigateToDetail, listLogViewModel = listLogViewModel)
        }
    }
}