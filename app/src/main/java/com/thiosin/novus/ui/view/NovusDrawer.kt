package com.thiosin.novus.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.annotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.thiosin.novus.R
import com.thiosin.novus.domain.model.Subreddit
import com.thiosin.novus.domain.model.User
import com.thiosin.novus.ui.theme.redditOrange
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun NovusDrawer(
    subreddits: List<Subreddit>,
    selected: Subreddit?,
    user: User?,
    onSubredditSelect: (Subreddit) -> Unit,
    onLogin: () -> Unit,
    onLogout: () -> Unit,
) {
    Column {
        HeaderSection(user, onLogin, onLogout)
        SubredditsSection(subreddits, selected, onSubredditSelect)
    }
}

@Composable
fun SubredditsSection(
    subreddits: List<Subreddit>,
    selected: Subreddit?,
    onClick: (Subreddit) -> Unit
) {
    ScrollableColumn() {
        subreddits.forEach {
            DrawerItem(
                subreddit = it,
                isSelected = it.queryName == selected?.queryName,
                onClick = onClick
            )
        }
    }
}

@Composable
fun HeaderSection(user: User?, onLogin: () -> Unit, onLogout: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        elevation = 8.dp
    ) {
        Column {
            Box(
                modifier = Modifier.preferredHeight(56.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                val title = annotatedString {
                    append("Novus for ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = redditOrange)) {
                        append("Reddit")
                    }
                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier.padding(8.dp)
                )
            }
            UserSection(user = user, onLogin = onLogin, onLogout = onLogout)
        }
    }
}

@Composable
fun UserSection(user: User?, onLogin: () -> Unit, onLogout: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Image(
                imageVector = vectorResource(id = R.drawable.ic_user),
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = user?.name ?: "Anonymous",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        IconButton(onClick = {
            if (user == null) {
                onLogin()
            } else {
                onLogout()
            }
        }) {
            val iconId = if (user == null) {
                R.drawable.ic_login
            } else {
                R.drawable.ic_logout
            }
            CoilImage(
                data = iconId,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun DrawerItem(subreddit: Subreddit, isSelected: Boolean, onClick: (Subreddit) -> Unit) {
    val border = if (isSelected) BorderStroke(2.dp, MaterialTheme.colors.secondary) else null
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable(onClick = { onClick(subreddit) }),
        elevation = 8.dp,
        border = border
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (subreddit.iconResource != null) {
                Image(
                    imageVector = vectorResource(id = subreddit.iconResource),
                    modifier = Modifier.size(28.dp)
                )
            } else {
                CoilImage(
                    data = if (subreddit.iconUrl.isNullOrBlank()) {
                        R.drawable.ic_reddit
                    } else {
                        subreddit.iconUrl
                    },
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(28.dp).clip(CircleShape)
                )
            }
            Text(
                text = subreddit.displayName,
                style = MaterialTheme.typography.button,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}