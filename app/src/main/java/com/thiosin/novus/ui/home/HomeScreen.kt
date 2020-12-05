package com.thiosin.novus.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.PagingData
import com.thiosin.novus.domain.model.SubmissionPreview
import com.thiosin.novus.ui.common.SubredditView
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeContent(
    showLoading: Boolean,
    subreddit: String,
    listState: Flow<PagingData<SubmissionPreview>>,
) {
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            Column {
                TopAppBar(title = { Text(text = "/r/$subreddit") })
                if (showLoading) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
            }
        },
        bodyContent = {
            Surface(color = MaterialTheme.colors.background) {
                SubredditView(
                    listFlow = listState,
                )
            }
        }
    )
}