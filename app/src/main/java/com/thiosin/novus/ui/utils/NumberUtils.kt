package com.thiosin.novus.ui.utils

import android.content.Context
import kotlin.math.absoluteValue

/**
 * Formats numbers larger than 1000 to k format
 *
 * e.g. 1234 -> 1.2k
 */
fun shortenToThousands(votes: Int): String {
    val abs = votes.absoluteValue
    return if (abs < 1000) {
        "$abs"
    } else {
        "%.${1}fk".format(abs / 1000.0)
    }
}

/**
 * Returns the width of the display in dp
 */
fun getDisplayWidth(context: Context): Float {
    val displayMetrics = context.resources.displayMetrics
    return displayMetrics.widthPixels / displayMetrics.density
}
