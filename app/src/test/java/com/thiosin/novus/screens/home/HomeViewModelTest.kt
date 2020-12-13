package com.thiosin.novus.screens.home

import co.zsmb.rainbowcake.test.assertDidObserve
import co.zsmb.rainbowcake.test.observeStateAndEvents
import com.thiosin.novus.domain.model.Subreddit
import com.thiosin.novus.domain.model.SubredditType
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
    val user = User(name = "username")
    val defaultSubreddit = Subreddit(
        queryName = "default queryName",
        displayName = "default displayName",
        type = SubredditType.Frontpage,
    )
    val nonDefaultSubreddit = Subreddit(
        queryName = "non default queryName",
        displayName = "non default displayName",
        type = SubredditType.Community,
    )

    Given("one default, one non default subreddit and no submissions") {
        coEvery { homePresenter.getDefaultSubreddit() } returns defaultSubreddit
        coEvery { homePresenter.getSubreddits() } returns listOf(
            defaultSubreddit,
            nonDefaultSubreddit
        )
        coEvery { homePresenter.getSubredditPage(any()) } returns listOf()
        And("no user") {
            coEvery { homePresenter.getUser() } returns null
            When("load without args") {
                Then("should acquire userless credentials") {
                    val vm = HomeViewModel(homePresenter)
                    vm.load()
                    coVerify { homePresenter.acquireUserlessCredentials() }
                }
                Then("default subreddit should be selected") {
                    val vm = HomeViewModel(homePresenter)
                    vm.observeStateAndEvents { stateObserver, eventsObserver ->
                        vm.load()

                        stateObserver.assertDidObserve(
                            HomeReady(
                                submissions = listOf(),
                                selectedSubreddit = defaultSubreddit,
                                subreddits = listOf(defaultSubreddit, nonDefaultSubreddit),
                                user = null
                            )
                        )
                    }
                }
            }
        }
        And("has user") {
            coEvery { homePresenter.getUser() } returns user
            When("load without args") {
                val vm = HomeViewModel(homePresenter)
                vm.load()
                Then("should NOT acquire userless credentials") {
                    coVerify(exactly = 0) { homePresenter.acquireUserlessCredentials() }
                }
            }
            When("load with non default subreddit") {
                Then("selected subreddit should be non default subreddit") {
                    val vm = HomeViewModel(homePresenter)
                    vm.observeStateAndEvents { stateObserver, _ ->
                        vm.load(nonDefaultSubreddit)

                        stateObserver.observed.last() shouldBe HomeReady(
                            submissions = listOf(),
                            selectedSubreddit = nonDefaultSubreddit,
                            subreddits = listOf(defaultSubreddit, nonDefaultSubreddit),
                            user = user
                        )
                    }
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

        // Always start from fresh
        if (testCase.description.isRootTest()) {
            clearAllMocks()
        }
        // Preserve answers inside containers for nested tests
        else if (testCase.description.isContainer()) {
            clearAllMocks(answers = false)
        }
    }
}
