package com.thiosin.novus.domain.model

import android.text.format.DateUtils

fun getRelativeTime(epochSec: Int): String {
    return DateUtils.getRelativeTimeSpanString(
        System.currentTimeMillis(),
        epochSec * 1_000L,
        DateUtils.SECOND_IN_MILLIS
    ).toString()
}

fun getVotesFormat(votes: Long): String {
    if (votes < 1000) {
        return votes.toString()
    }
    val k = votes / 1000.0
    return "%.${1}fk".format(k)
}