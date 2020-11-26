package com.thiosin.novus.ui.list

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PostCard(title: String) {
    Card(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
        Column {
            Column(Modifier.padding(8.dp)) {
                Text(text = title, style = MaterialTheme.typography.h6)
            }
        }
    }
}