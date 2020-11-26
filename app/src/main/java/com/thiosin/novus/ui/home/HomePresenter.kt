package com.thiosin.novus.ui.home

import kotlinx.coroutines.delay
import javax.inject.Inject

class HomePresenter @Inject constructor() {

    suspend fun getData(): List<String> {
        delay(2_000L)
        return (0..100).toList().map { "Item $it" }
    }

    suspend fun getNextData(): List<String> {
        delay(2000L)
        return (101..200).toList().map { "Item $it" }
    }
}