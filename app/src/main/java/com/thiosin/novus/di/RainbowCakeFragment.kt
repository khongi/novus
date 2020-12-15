package com.thiosin.novus.di

import androidx.lifecycle.ViewModelProvider
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import co.zsmb.rainbowcake.base.ViewModelScope

/**
 * Fetch the appropriate ViewModel instance for the Fragment.
 *
 * @param scope The scope that the ViewModel should be fetched from and exist in.
 *              See [ViewModelScope] for details.
 */
inline fun <F : RainbowCakeFragment<VS, VM>, VS, reified VM : RainbowCakeViewModel<VS>> F.getViewModel(
    scope: ViewModelScope = ViewModelScope.Default,
): VM {
    return when (scope) {
        ViewModelScope.Default -> {
            ViewModelProvider(this).get(VM::class.java)
        }
        is ViewModelScope.ParentFragment -> {
            val parentFragment = getParentFragment()
                ?: throw IllegalStateException("No parent Fragment")
            val key = scope.key
            if (key != null) {
                ViewModelProvider(parentFragment).get(key, VM::class.java)
            } else {
                ViewModelProvider(parentFragment).get(VM::class.java)
            }
        }
        is ViewModelScope.Activity -> {
            val key = scope.key
            if (key != null) {
                ViewModelProvider(requireActivity()).get(key, VM::class.java)
            } else {
                ViewModelProvider(requireActivity()).get(VM::class.java)
            }
        }
    }
}
