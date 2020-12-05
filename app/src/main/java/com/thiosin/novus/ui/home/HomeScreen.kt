package com.thiosin.novus.ui.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.PagingData
import com.thiosin.novus.domain.model.SubmissionPreview
import com.thiosin.novus.ui.common.SubmissionList
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeContent(
    subreddit: String,
    listState: Flow<PagingData<SubmissionPreview>>,
) {
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            TopAppBar(title = { Text(text = "/r/$subreddit") })
        },
        bodyContent = {
            Surface(color = MaterialTheme.colors.background) {
                SubmissionList(
                    listFlow = listState,
                )
            }
        }
    )
}