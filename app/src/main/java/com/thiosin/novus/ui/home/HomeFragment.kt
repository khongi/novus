package com.thiosin.novus.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.ui.tooling.preview.Preview
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import com.thiosin.novus.di.getViewModel
import com.thiosin.novus.ui.NovusTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : RainbowCakeFragment<HomeViewState, HomeViewModel>() {

    override fun provideViewModel() = getViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                HomeScreenContent()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.load()
    }

    @Composable
    private fun HomeScreenContent(text: String = "") {
        NovusTheme {
            Scaffold(topBar = {
                TopAppBar(title = { Text(text = "Hot") })
            },
            bodyContent = {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting(text)
                }
            })
        }
    }

    @Composable
    fun Greeting(name: String) {
        Text(text = "Hello $name!")
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        HomeScreenContent("Preview")
    }

    override fun render(viewState: HomeViewState) {
        (view as ComposeView).setContent {
            when (viewState) {
                is Initial -> HomeScreenContent("Initial")
                is Loading -> HomeScreenContent("Loading...")
                is HomeReady -> HomeScreenContent(viewState.data)
            }
        }
    }
}