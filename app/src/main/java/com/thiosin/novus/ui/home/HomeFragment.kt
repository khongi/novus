package com.thiosin.novus.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.paging.PagingData
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import com.thiosin.novus.di.getViewModel
import com.thiosin.novus.domain.model.SubmissionPreview
import com.thiosin.novus.ui.common.SubredditView
import com.thiosin.novus.ui.theme.NovusTheme
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
                    is HomeInitial -> Unit
                    is HomeContent -> HomeContentScreen(
                        showLoading = viewState.showLoading,
                        listState = viewState.listFlow,
                        title = viewState.title
                    )
                }
            }
        }
    }

    @Composable
    private fun HomeContentScreen(
        showLoading: Boolean,
        title: String,
        listState: Flow<PagingData<SubmissionPreview>>
    ) {
        Scaffold(
            modifier = Modifier.fillMaxWidth(),
            topBar = {
                Column {
                    TopAppBar(title = { Text(text = title) })
                    if (showLoading) {
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                    }
                }
            },
            bodyContent = {
                Surface(color = MaterialTheme.colors.background) {
                    SubredditView(
                        listFlow = listState,
                    )
                }
            }
        )
    }
}