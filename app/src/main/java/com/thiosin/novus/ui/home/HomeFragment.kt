package com.thiosin.novus.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import com.thiosin.novus.di.getViewModel
import com.thiosin.novus.ui.NovusTheme
import com.thiosin.novus.ui.list.ListPage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : RainbowCakeFragment<HomeViewState, HomeViewModel>() {

    override fun provideViewModel() = getViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                NovusTheme {
                    viewModel.state.observeAsState().value?.let { homeViewState ->
                        when (homeViewState) {
                            is Initial -> {
                                // TODO
                            }
                            is Content -> {
                                Scaffold(
                                    topBar = {
                                        if (homeViewState.showLoading) {
                                            LinearProgressIndicator()
                                        }
                                    },
                                    bodyContent = {
                                        // A surface container using the 'background' color from the theme
                                        Surface(color = MaterialTheme.colors.background) {
                                            ListPage(
                                                listState = homeViewState.listState,
                                                onListEnd = viewModel::loadNext
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.load()
    }

//
//    @Preview(showBackground = true)
//    @Composable
//    fun DefaultPreview() {
//        HomeScreenContent((0..10).toList().map { "Item $it" })
//    }

    override fun render(viewState: HomeViewState) {
    }
}