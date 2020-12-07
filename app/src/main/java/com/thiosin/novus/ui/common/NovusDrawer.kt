package com.thiosin.novus.ui.common

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.annotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.thiosin.novus.R
import com.thiosin.novus.domain.model.Subreddit
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun NovusDrawer(subreddits: List<Subreddit>, selected: Subreddit?, onClick: (Subreddit) -> Unit) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.primaryVariant)
        ) {
            Text(
                text = annotatedString {
                    pushStyle(SpanStyle(color = MaterialTheme.colors.onPrimary))
                    append("Novus for ")
                    pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                    append("Reddit")
                },
                style = MaterialTheme.typography.h1,
                modifier = Modifier.padding(8.dp)
            )
        }
        ScrollableColumn() {
            subreddits.forEach {
                DrawerItem(
                    subreddit = it,
                    isSelected = it.name == selected?.name,
                    onClick = onClick
                )
            }
        }
    }
}

@Composable
fun DrawerItem(subreddit: Subreddit, isSelected: Boolean, onClick: (Subreddit) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable(onClick = { onClick(subreddit) }),
        backgroundColor = if (isSelected) {
            MaterialTheme.colors.secondary
        } else {
            MaterialTheme.colors.background
        },
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoilImage(
                data = if (subreddit.icon.isBlank().not()) {
                    subreddit.icon
                } else {
                    R.drawable.ic_reddit_orange
                },
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(28.dp).clip(CircleShape)
            )
            Text(
                text = subreddit.displayName,
                style = MaterialTheme.typography.button,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}