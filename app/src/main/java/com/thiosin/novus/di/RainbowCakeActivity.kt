package com.thiosin.novus.di

import androidx.lifecycle.ViewModelProvider
import co.zsmb.rainbowcake.base.RainbowCakeActivity
import co.zsmb.rainbowcake.base.RainbowCakeViewModel

/**
 * Fetch the appropriate ViewModel instance for the Activity.
 */
inline fun <A : RainbowCakeActivity<VS, VM>, VS, reified VM : RainbowCakeViewModel<VS>> A.getViewModel(): VM {
    return ViewModelProvider(this).get(VM::class.java)
}