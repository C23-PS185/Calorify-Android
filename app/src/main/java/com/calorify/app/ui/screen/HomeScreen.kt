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
    calorieNeeded: Float,
    listLogViewModel: ListLogViewModel,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier){
    Column (modifier = modifier) {
        HomeStickyHeader(name = "Melati", photo = "https://media.licdn.com/dms/image/C4E03AQHzTBTfofQsig/profile-displayphoto-shrink_800_800/0/1616565306427?e=1690416000&v=beta&t=z7qPZl4pHH1o5220VLLO0ZofQ2Nj4W-dYBY2vyADeBY")
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding()
        ){
            ListLog(calorieNeeded = calorieNeeded, "eat time", navigateToDetail = navigateToDetail, listLogViewModel = listLogViewModel)
        }
    }
}