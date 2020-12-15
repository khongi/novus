package com.thiosin.novus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.thiosin.novus.databinding.ActivityMainBinding
import com.thiosin.novus.ui.theme.NovusTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NovusTheme {
                AndroidViewBinding(ActivityMainBinding::inflate)
            }
        }
    }
}
