package com.thiosin.novus.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import com.thiosin.novus.di.getViewModel
import com.thiosin.novus.ui.theme.NovusTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : RainbowCakeFragment<LoginViewState, LoginViewModel>() {

    override fun provideViewModel() = getViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                NovusTheme { }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.startLogin()
    }

    override fun render(viewState: LoginViewState) {
        (view as ComposeView).setContent {
            NovusTheme {
                when (viewState) {
                    is LoginInitial -> Unit
                    is LoginStart -> {
                        LoginScreen(
                            authUrl = viewState.authUrl,
                            redirectUrl = viewState.redirectUrl,
                            onPageStart = { url -> viewModel.onPageStart(url) },
                            onAbort = { findNavController().popBackStack() }
                        )
                    }
                    is LoginComplete -> {
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }
}
