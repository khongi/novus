package com.thiosin.novus.domain.model

import android.text.format.DateUtils
import java.time.Instant
import java.time.ZoneOffset

fun getRelativeTime(epochSecUTC: Long): String {
    val now = Instant.now().toEpochMilli()
    val time = Instant
        .ofEpochSecond(epochSecUTC)
        .atOffset(ZoneOffset.UTC)
        .toEpochSecond() * 1_000L
    return DateUtils.getRelativeTimeSpanString(
        time,
        now,
        DateUtils.SECOND_IN_MILLIS
    ).toString()
}
