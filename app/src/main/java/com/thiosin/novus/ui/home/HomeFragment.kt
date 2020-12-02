package com.thiosin.novus.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.emptyContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.paging.PagingData
import androidx.ui.tooling.preview.Preview
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import com.thiosin.novus.di.getViewModel
import com.thiosin.novus.ui.NovusTheme
import com.thiosin.novus.ui.common.ListView
import com.thiosin.novus.ui.common.PostItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow

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
                NovusTheme { }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.load()
    }

    override fun render(viewState: HomeViewState) {
        (view as ComposeView).setContent {
            NovusTheme {
                when (viewState) {
                    is HomeInitial -> HomeInitialScreen()
                    is HomeContent -> HomeContentScreen(
                        showLoading = viewState.showLoading,
                        listState = viewState.listFlow
                    )
                }
            }
        }
    }


    @Composable
    private fun HomeScreen(
        topBar: @Composable () -> Unit = emptyContent(),
        bodyContent: @Composable (PaddingValues) -> Unit
    ) {
        Scaffold(
            modifier = Modifier.fillMaxWidth(),
            topBar = topBar,
            bodyContent = bodyContent
        )
    }

    @Composable
    private fun HomeInitialScreen() {
        HomeScreen(
            topBar = {
                Column {
                    TopAppBar(title = { Text(text = "Initial") })
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
            },
            bodyContent = {

            }
        )
    }

    @Composable
    private fun HomeContentScreen(
        showLoading: Boolean,
        listState: Flow<PagingData<String>>
    ) {
        HomeScreen(
            topBar = {
                Column {
                    TopAppBar(title = { Text(text = "Title") })
                    if (showLoading) {
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                    }
                }
            },
            bodyContent = {
                Surface(color = MaterialTheme.colors.background) {
                    ListView(
                        listFlow = listState,
                    )
                }
            }
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        PostItem(title = "Preview")
    }
}