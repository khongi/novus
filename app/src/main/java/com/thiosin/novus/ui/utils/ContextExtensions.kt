package com.thiosin.novus.ui.utils

import android.content.Context

/**
 * Returns the width of the display in dp
 */
val Context.displayWidth: Float
    get() {
        val displayMetrics = resources.displayMetrics
        return displayMetrics.widthPixels / displayMetrics.density
    }