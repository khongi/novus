package com.thiosin.novus.ui.common

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.thiosin.novus.domain.model.Subreddit
import dev.chrisbanes.accompanist.coil.CoilImage

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
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            .clickable(onClick = { onClick(subreddit) }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        CoilImage(
            data = subreddit.icon,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(28.dp).clip(CircleShape)
        )
        Text(
            text = subreddit.displayName,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}