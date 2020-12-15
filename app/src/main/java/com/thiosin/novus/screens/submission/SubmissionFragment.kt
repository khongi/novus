package com.thiosin.novus.screens.submission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import com.thiosin.novus.di.getViewModel
import com.thiosin.novus.ui.utils.getDisplayWidth
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
                val state = viewModel.state.observeAsState()
                state.value?.let { viewState ->
                    when (viewState) {
                        is SubmissionReadyState -> {
                            SubmissionScreen(
                                viewState = viewState,
                                onNavigationClick = { findNavController().popBackStack() },
                                onLinkClick = { url -> showWebScreen(url) },
                                onVoteClick = { fullname, liked ->
                                    viewModel.vote(fullname, liked)
                                },
                                onCommentVoteClick = { fullname, liked ->
                                    viewModel.vote(fullname, liked, isComment = true)
                                }
                            )
                        }
                        SubmissionInitial -> Unit
                    }
                }
            }
        }
    }

    private fun showWebScreen(url: String) {
        findNavController().navigate(
            SubmissionFragmentDirections.actionSubmissionFragmentToWebFragment(url)
        )
    }
}
