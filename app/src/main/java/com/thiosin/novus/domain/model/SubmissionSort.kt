package com.thiosin.novus.domain.model

import java.util.*

enum class SubmissionSort {
    Hot,
    New;

    fun getApiValue() = this.name.toLowerCase(Locale.ROOT)
}
