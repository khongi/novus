package com.thiosin.novus.screens.submission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import com.thiosin.novus.di.getViewModel
import com.thiosin.novus.ui.theme.NovusTheme
import com.thiosin.novus.ui.view.NavigationIcon
import com.thiosin.novus.ui.view.NovusTopAppBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubmissionFragment : RainbowCakeFragment<SubmissionViewState, SubmissionViewModel>() {
    override fun provideViewModel() = getViewModel()
    override fun render(viewState: SubmissionViewState) = Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return ComposeView(inflater.context).apply {
            setContent {
                NovusTheme {
                    val state = viewModel.state.observeAsState()
                    state.value?.let {
                        SubmissionScreen(viewState = it)
                    }
                }
            }
        }
    }

    @Composable
    private fun SubmissionScreen(viewState: SubmissionViewState) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                NovusTopAppBar(
                    title = "TODO",
                    navIcon = NavigationIcon.Back,
                    onNavigationIconClick = { findNavController().popBackStack() }
                )
            },
            bodyContent = {
                Text(text = SubmissionFragmentArgs.fromBundle(requireArguments()).submissionFullname)
            }
        )
    }
}