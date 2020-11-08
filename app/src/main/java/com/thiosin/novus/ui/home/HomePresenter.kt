package com.thiosin.novus.ui.home

import kotlinx.coroutines.delay
import javax.inject.Inject

class HomePresenter @Inject constructor() {

    suspend fun getData(): String {
        delay(2_000L)
        return "Data"
    }
}