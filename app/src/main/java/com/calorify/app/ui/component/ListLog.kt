package com.calorify.app.ui.component

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.calorify.app.R
import com.calorify.app.model.LogKalori
import com.calorify.app.repository.LogRepository
import com.calorify.app.ui.theme.CalorifyTheme
import com.calorify.app.viewmodel.ListLogViewModel
import com.calorify.app.viewmodel.ViewModelFactory2
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListLog(
    groupedBy: String,
    modifier: Modifier = Modifier,
    viewModel: ListLogViewModel = viewModel(
        factory = ViewModelFactory2(
            LogRepository(
                LocalContext.current
            )
        )
    ),
    navigateToDetail: (Int) -> Unit,
) {
    val groupedLogEatTime by viewModel.groupedEatTimeLogKalori.collectAsState()

    val groupedLogDate by viewModel.groupedDateLogKalori.collectAsState()

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
                    HistoryLogHeader()
                } else {
                    HomeHeader()
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
                        title = log.title,
                        photoUrl = log.photo,
                        calorie = log.totalCalories,
                        time = log.createdAtTime,
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

@Preview(showBackground = true)
@Composable
fun ListLogPreview() {
    CalorifyTheme {
        ListLog("date", navigateToDetail = {})
    }
}