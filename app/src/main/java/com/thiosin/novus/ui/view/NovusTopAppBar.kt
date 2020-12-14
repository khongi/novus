package com.thiosin.novus.ui.view

import androidx.compose.foundation.Image
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.vectorResource
import com.thiosin.novus.R


const val AppBarTitleTestTag = "AppBarTitleTestTag"
const val NavIconTestTag = "NavIconTag"

@Composable
fun NovusTopAppBar(
    title: String,
    navIcon: NavigationIcon = NavigationIcon.NoIcon,
    onNavigationIconClick: () -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(text = title,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.testTag(AppBarTitleTestTag))
        },
        backgroundColor = MaterialTheme.colors.surface,
        navigationIcon = {
            if (navIcon != NavigationIcon.NoIcon) {
                IconButton(
                    onClick = onNavigationIconClick,
                    modifier = Modifier.testTag(NavIconTestTag)
                ) {
                    val iconId = when (navIcon) {
                        NavigationIcon.Back -> R.drawable.ic_left_arrow
                        NavigationIcon.Menu -> R.drawable.ic_menu
                        NavigationIcon.Close -> R.drawable.ic_close
                        NavigationIcon.NoIcon -> null
                    }
                    iconId?.let {
                        Image(imageVector = vectorResource(id = iconId))
                    }
                }
            }
        }
    )
}

enum class NavigationIcon {
    Back,
    Menu,
    Close,
    NoIcon
}