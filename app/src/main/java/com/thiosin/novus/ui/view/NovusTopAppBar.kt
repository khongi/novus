package com.thiosin.novus.ui.view

import androidx.compose.foundation.Image
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.thiosin.novus.R

@Composable
fun NovusTopAppBar(
    title: String,
    navIcon: NavigationIcon = NavigationIcon.NoIcon,
    onNavigationIconClick: () -> Unit = {},
) {
    TopAppBar(
        title = { Text(text = title, color = MaterialTheme.colors.primary) },
        backgroundColor = MaterialTheme.colors.surface,
        navigationIcon = {
            if (navIcon != NavigationIcon.NoIcon) {
                IconButton(
                    onClick = onNavigationIconClick
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