package com.thiosin.novus.kotest

import co.zsmb.rainbowcake.ioContext
import io.kotest.core.listeners.TestListener
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@ExperimentalCoroutinesApi
class PresenterTestListener : TestListener {

    private val testDispatcher = TestCoroutineDispatcher()

    override suspend fun beforeTest(testCase: TestCase) {
        @Suppress("DEPRECATION")
        ioContext = testDispatcher
        Dispatchers.setMain(testDispatcher)
    }

    override suspend fun afterTest(testCase: TestCase, result: TestResult) {
        @Suppress("DEPRECATION")
        ioContext = Dispatchers.IO
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}
