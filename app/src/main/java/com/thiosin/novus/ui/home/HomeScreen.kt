package com.thiosin.novus.ui.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.loadVectorResource
import androidx.paging.PagingData
import com.thiosin.novus.R
import com.thiosin.novus.domain.model.SubmissionPreview
import com.thiosin.novus.ui.common.SubmissionList
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeContent(
    subreddit: String,
    listState: Flow<PagingData<SubmissionPreview>>,
    onLinkClicked: (String) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            TopAppBar(
                title = { Text(text = "/r/$subreddit") },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        loadVectorResource(id = R.drawable.ic_baseline_menu_24).resource.resource?.let {
                            Icon(asset = it)
                        }
                    }
                }
            )
        },
        bodyContent = {
            Surface(color = MaterialTheme.colors.background) {
                SubmissionList(
                    listFlow = listState,
                    onLinkClicked = onLinkClicked
                )
            }
        }
    )
}