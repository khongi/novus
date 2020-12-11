package com.thiosin.novus.screens.submission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import com.thiosin.novus.di.getViewModel
import com.thiosin.novus.ui.theme.NovusTheme
import com.thiosin.novus.ui.utils.getDisplayWidth
import com.thiosin.novus.ui.view.NavigationIcon
import com.thiosin.novus.ui.view.NovusTopAppBar
import com.thiosin.novus.ui.view.SubmissionDetails
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubmissionFragment : RainbowCakeFragment<SubmissionViewState, SubmissionViewModel>() {
    override fun provideViewModel() = getViewModel()
    override fun render(viewState: SubmissionViewState) = Unit

    override fun onStart() {
        super.onStart()
        val displayWidthDp = getDisplayWidth(requireContext())
        val submission = SubmissionFragmentArgs.fromBundle(requireArguments()).submission
        viewModel.load(submission, displayWidthDp)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return ComposeView(inflater.context).apply {
            setContent {
                NovusTheme {
                    val state = viewModel.state.observeAsState()
                    state.value?.let { viewState ->
                        when (viewState) {
                            is SubmissionReadyState -> {
                                SubmissionScreen(
                                    viewState = viewState,
                                    onLinkClick = { url -> showWebScreen(url) },
                                    onVoteClick = { fullname, liked ->
                                        viewModel.vote(fullname, liked)
                                    }
                                )
                            }
                            SubmissionInitial -> Unit
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun SubmissionScreen(
        viewState: SubmissionReadyState,
        onLinkClick: (String) -> Unit,
        onVoteClick: (String, Boolean?) -> Unit,
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                NovusTopAppBar(
                    title = "Comments",
                    navIcon = NavigationIcon.Back,
                    onNavigationIconClick = { findNavController().popBackStack() }
                )
            },
            bodyContent = {
                if (viewState.loading) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
                SubmissionDetails(
                    submission = viewState.submission,
                    comments = viewState.comments,
                    displayWidthDp = viewState.displayWidthDp,
                    onLinkClick = onLinkClick,
                    onVoteClick = onVoteClick
                )
            }
        )
    }

    private fun showWebScreen(url: String) {
        findNavController().navigate(
            SubmissionFragmentDirections.actionSubmissionFragmentToWebFragment(url)
        )
    }
}