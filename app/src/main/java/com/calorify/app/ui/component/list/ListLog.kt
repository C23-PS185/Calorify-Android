package com.calorify.app.ui.component.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.calorify.app.R
import com.calorify.app.ui.component.button.ScrollToTopButton
import com.calorify.app.ui.component.header.HistoryLogHeader
import com.calorify.app.ui.component.header.HomeHeader
import com.calorify.app.ui.component.header.LogHeader
import com.calorify.app.viewmodel.ListLogViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListLog(
    calorieNeeded: Int = 0,
    groupedBy: String,
    modifier: Modifier = Modifier,
    listLogViewModel: ListLogViewModel,
    navigateToDetail: (String) -> Unit,
) {

    val groupedLogEatTime by listLogViewModel.groupedEatTimeLogKalori.collectAsState()

    val groupedLogDate by listLogViewModel.groupedDateLogKalori.collectAsState()

    val calorieFulfilled by listLogViewModel.calorieFulfilled.collectAsState()

    val monthlyCalorieFulfilled by listLogViewModel.monthlyCalorieFullfiled.collectAsState()

    var groupedLog = if (groupedBy == "date") {
        groupedLogDate
    } else {
        groupedLogEatTime
    }


    Box(modifier = modifier) {
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showButton: Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 }
        }

        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            item {
                if (groupedBy == "date"){
                    HistoryLogHeader(monthlyCalorieFulfilled, viewModel = listLogViewModel)
                } else {
                    HomeHeader(calorieNeeded = calorieNeeded, calorieFulfilled = calorieFulfilled)
                }
                if (groupedLog.isNullOrEmpty()) {
                    AsyncImage(
                        model = "https://img.freepik.com/free-vector/no-data-concept-illustration_114360-536.jpg?w=740&t=st=1684077121~exp=1684077721~hmac=8b22357b8bac38612858784d51485acd5684cb5a81669631d67249675dd07e0f",
                        contentDescription = stringResource(R.string.no_data_img),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(16.dp)
                            .size(200.dp)
                            .clip(CircleShape)
                    )
                    Text(
                        text = stringResource(R.string.data_not_found),
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }
            }

            groupedLog.forEach { (groupedTitle, logs) ->
                stickyHeader {
                    LogHeader(groupedTitle)
                }
                items(logs, key = { it.logId }) { log ->
                    LogListItem(
                        title = log.foodName!!,
                        photoUrl = log.imageUrl!!,
                        calorie = log.foodCalories!!,
                        time = log.createdAtTime!!,
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItemPlacement(tween(durationMillis = 100))
                            .clickable {
                                navigateToDetail(log.logId)
                            }
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(Alignment.BottomCenter)
        ) {
            ScrollToTopButton(
                onClick = {
                    scope.launch {
                        listState.scrollToItem(index = 0)
                    }
                }
            )
        }
    }
}