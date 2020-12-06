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
fun NovusDrawer(drawerItems: List<Subreddit>, onClick: (String) -> Unit) {

    ScrollableColumn() {
        Text(
            text = "Novus",
            style = MaterialTheme.typography.h2
        )
        drawerItems.forEach {
            DrawerItem(name = it.name, onClick = onClick)
        }
    }
}

@Composable
fun DrawerItem(name: String, onClick: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = { onClick(name) })
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.h5,
        )
    }
}