package com.thiosin.novus.ui.common

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import kotlinx.coroutines.flow.Flow

@Composable
fun ListView(listFlow: Flow<PagingData<String>>) {
    val listItems = listFlow.collectAsLazyPagingItems()

    LazyColumn {

        items(listItems) { item ->
            PostItem(title = item)
        }

        listItems.run {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingScreen(modifier = Modifier.fillParentMaxSize()) }
                }
                loadState.append is LoadState.Loading -> {
                    item { LoadingItem() }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage ?: "",
                            modifier = Modifier.fillParentMaxSize(),
                            onClickRetry = { retry() }
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    val e = loadState.append as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage ?: "",
                            onClickRetry = { retry() }
                        )
                    }
                }
            }
        }

    }
}

