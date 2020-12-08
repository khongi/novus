package com.thiosin.novus.ui.view

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.vectorResource
import com.thiosin.novus.R

@Composable
fun NovusTopAppBar(
    title: String,
    navIcon: NavigationIcon = NavigationIcon.NoIcon,
    onNavigationIconClick: () -> Unit = {},
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (navIcon != NavigationIcon.NoIcon) {
                IconButton(
                    icon = {
                        when (navIcon) {
                            NavigationIcon.Back -> Icon(asset = vectorResource(id = R.drawable.ic_baseline_arrow_back_24))
                            NavigationIcon.Menu -> Icon(asset = vectorResource(id = R.drawable.ic_baseline_menu_24))
                            NavigationIcon.Close -> Icon(asset = vectorResource(id = R.drawable.ic_baseline_close_24))
                            NavigationIcon.NoIcon -> Unit
                        }
                    },
                    onClick = onNavigationIconClick
                )
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