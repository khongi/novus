package com.thiosin.novus.ui.common

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thiosin.novus.domain.model.Subreddit

@Composable
fun NovusDrawer(subreddits: List<Subreddit>, onClick: (Subreddit) -> Unit) {

    ScrollableColumn() {
        Text(
            text = "Novus",
            style = MaterialTheme.typography.h2
        )
        subreddits.forEach {
            DrawerItem(subreddit = it, onClick = onClick)
        }
    }
}

@Composable
fun DrawerItem(subreddit: Subreddit, onClick: (Subreddit) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = { onClick(subreddit) })
    ) {
        Text(
            text = subreddit.displayName,
            style = MaterialTheme.typography.h5,
        )
    }
}