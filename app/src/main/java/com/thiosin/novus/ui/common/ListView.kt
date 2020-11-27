package com.thiosin.novus.ui.common

import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.onActive
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

@Composable
fun ListView(listState: StateFlow<List<String>>, onListEnd: () -> Unit) {
    val state = listState.collectAsState()
    val lastIndex = state.value.lastIndex
    LazyColumnForIndexed(items = state.value) { index, item ->
        Timber.d("Index $index")
        if (index == lastIndex) {
            Timber.d("LAST INDEX")
            onActive {
                Timber.d("Getting new list")
                onListEnd()
            }
        }
        PostCard(title = item)
    }
}

