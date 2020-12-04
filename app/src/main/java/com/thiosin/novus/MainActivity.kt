package com.thiosin.novus

import android.os.Bundle
import co.zsmb.rainbowcake.navigation.SimpleNavActivity
import com.thiosin.novus.ui.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : SimpleNavActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            navigator.add(LoginFragment())
        }
    }
}
