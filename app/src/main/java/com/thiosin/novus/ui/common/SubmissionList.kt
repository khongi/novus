package com.thiosin.novus.ui.common

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ContextAmbient
import com.thiosin.novus.domain.model.SubmissionPreview

@Composable
fun SubmissionList(
    submissions: List<SubmissionPreview>,
    listState: LazyListState,
    onLinkClick: (String) -> Unit,
    onListEnd: () -> Unit,
) {
    if (listState.firstVisibleItemIndex >= submissions.size - 10) {
        onListEnd()
    }
    val displayMetrics = ContextAmbient.current.resources.displayMetrics
    val displayWidth = remember { displayMetrics.widthPixels / displayMetrics.density }
    LazyColumn(state = listState) {
        items(submissions) { submission ->
            SubmissionPreviewItem(submission = submission, displayWidth, onLinkClick = onLinkClick)
        }

//        listItems.run {
//            when {
//                loadState.refresh is LoadState.Loading -> {
//                    item { LoadingScreen(modifier = Modifier.fillParentMaxSize()) }
//                }
//                loadState.append is LoadState.Loading -> {
//                    item { LoadingItem() }
//                }
//                loadState.refresh is LoadState.Error -> {
//                    val e = loadState.refresh as LoadState.Error
//                    item {
//                        ErrorItem(
//                            message = e.error.localizedMessage ?: "",
//                            modifier = Modifier.fillParentMaxSize(),
//                            onClickRetry = { retry() }
//                        )
//                    }
//                }
//                loadState.append is LoadState.Error -> {
//                    val e = loadState.append as LoadState.Error
//                    item {
//                        ErrorItem(
//                            message = e.error.localizedMessage ?: "",
//                            onClickRetry = { retry() }
//                        )
//                    }
//                }
//            }
//        }

    }
}

