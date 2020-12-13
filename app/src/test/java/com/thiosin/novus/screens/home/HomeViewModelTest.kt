package com.thiosin.novus.screens.home

import co.zsmb.rainbowcake.test.observeStateAndEvents
import com.thiosin.novus.domain.model.User
import com.thiosin.novus.kotest.ViewModelTestListener
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk

class HomeViewModelTest : BehaviorSpec({

    val homePresenter: HomePresenter = mockk(relaxUnitFun = true)
    val user: User = mockk()

    Given("Initial state without user") {
        val vm = HomeViewModel(homePresenter)
        coEvery { homePresenter.getUser() } returns null

        When ("load without args") {
            Then ("should acquire userless credentials") {
                vm.observeStateAndEvents { stateObserver, eventsObserver ->
                    vm.load()

                    coVerify { homePresenter.acquireUserlessCredentials() }
                }
            }
        }
    }

    Given("Initial state with user") {
        val vm = HomeViewModel(homePresenter)
        coEvery { homePresenter.getUser() } returns user

        When ("load without args") {
            Then ("should NOT acquire userless credentials") {
                vm.observeStateAndEvents { stateObserver, eventsObserver ->
                    vm.load()

                    coVerify(exactly = 0) { homePresenter.acquireUserlessCredentials() }
                }
            }
        }
    }

}) {
    init {
        listener(ViewModelTestListener())
    }

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
        if (testCase.description.isRootTest()) {
            clearAllMocks()
        }
    }
}
