package com.thiosin.novus.screens.home

import co.zsmb.rainbowcake.test.assertDidObserve
import co.zsmb.rainbowcake.test.observeStateAndEvents
import com.thiosin.novus.creators.getSubmission
import com.thiosin.novus.domain.model.Subreddit
import com.thiosin.novus.domain.model.SubredditType
import com.thiosin.novus.domain.model.User
import com.thiosin.novus.kotest.ViewModelTestListener
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.collections.shouldContainAll
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
    val submissionOne = getSubmission(id = "submission 1", fullname = "submission 1 fullname")
    val submissionTwo = getSubmission(id = "submission 2")

    Given("no setup") {
        When("called nothing") {
            Then("state should be empty loading") {
                val vm = HomeViewModel(homePresenter)
                vm.observeStateAndEvents { stateObserver, _ ->
                    stateObserver.observed shouldBe listOf(HomeEmptyLoading)
                }
            }
        }
    }

    Given("one default, one non default subreddit and no submissions") {
        coEvery { homePresenter.getDefaultSubreddit() } returns defaultSubreddit
        coEvery { homePresenter.getSubreddits() } returns listOf(
            defaultSubreddit,
            nonDefaultSubreddit
        )
        coEvery { homePresenter.getSubredditPage(any()) } returns listOf()
        And("no user") {
            coEvery { homePresenter.getUser() } returns null
            When("initial load") {
                Then("should acquire userless credentials") {
                    val vm = HomeViewModel(homePresenter)
                    vm.load()
                    coVerify { homePresenter.acquireUserlessCredentials() }
                }
                Then("default subreddit should be selected") {
                    val vm = HomeViewModel(homePresenter)
                    vm.observeStateAndEvents { stateObserver, _ ->
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
            When("load then startLoading") {
                Then("should switch to loading state") {
                    val vm = HomeViewModel(homePresenter)
                    vm.observeStateAndEvents { stateObserver, _ ->
                        vm.load()
                        vm.startLoading()

                        stateObserver.observed shouldContainAll listOf(HomeEmptyLoading, HomeEmptyLoading)
                    }
                }
            }
            When("load then switch to non default subreddit then load again (pop behavior)") {
                Then("non default subreddit should remain selected") {
                    val vm = HomeViewModel(homePresenter)
                    vm.observeStateAndEvents { stateObserver, _ ->
                        vm.load()
                        vm.switchSubreddit(nonDefaultSubreddit)
                        vm.load()

                        val expectedStateAfterSwitch = HomeReady(
                            submissions = listOf(),
                            selectedSubreddit = nonDefaultSubreddit,
                            subreddits = listOf(defaultSubreddit, nonDefaultSubreddit),
                            user = null
                        )

                        stateObserver.observed.last() shouldBe expectedStateAfterSwitch
                    }
                }
            }
            When("load then switch to non default") {
                Then("selected subreddit should change to non default") {
                    val vm = HomeViewModel(homePresenter)
                    vm.observeStateAndEvents { stateObserver, _ ->
                        vm.load()
                        vm.switchSubreddit(nonDefaultSubreddit)

                        val expectedStateAfterLoad = HomeReady(
                            submissions = listOf(),
                            selectedSubreddit = defaultSubreddit,
                            subreddits = listOf(defaultSubreddit, nonDefaultSubreddit),
                            user = null
                        )

                        stateObserver.observed shouldContainAll listOf(
                            expectedStateAfterLoad,
                            expectedStateAfterLoad.copy(selectedSubreddit = nonDefaultSubreddit)
                        )
                    }
                }
            }
        }
        And("has user") {
            coEvery { homePresenter.getUser() } returns user
            When("load") {
                val vm = HomeViewModel(homePresenter)
                vm.load()
                Then("should NOT acquire userless credentials") {
                    coVerify(exactly = 0) { homePresenter.acquireUserlessCredentials() }
                }
            }
            When("load twice") {
                val vm = HomeViewModel(homePresenter)

                vm.load()
                vm.load()
                Then("user should be loaded only once") {
                    coVerify(exactly = 1) { homePresenter.getUser() }
                }
                Then("subreddits should be loaded only once") {
                    coVerify(exactly = 1) { homePresenter.getSubreddits() }
                }
                Then("default subreddit should be loaded only once") {
                    coVerify(exactly = 1) { homePresenter.getDefaultSubreddit() }
                }
                Then("submissions should be loaded twice") {
                    coVerify(exactly = 2) { homePresenter.getSubredditPage(defaultSubreddit.queryName) }
                }
            }
        }
    }
    Given("default subreddit has submissionOne at first page and submissionTwo at second page") {
        coEvery { homePresenter.getDefaultSubreddit() } returns defaultSubreddit
        coEvery { homePresenter.getSubreddits() } returns listOf(
            defaultSubreddit,
            nonDefaultSubreddit
        )
        coEvery { homePresenter.getSubredditPage(defaultSubreddit.queryName) } returns listOf(submissionOne)
        coEvery {
            homePresenter.getSubredditPage(
                subreddit = defaultSubreddit.queryName,
                count = 1,
                after = submissionOne.fullname
            )
        } returns listOf(submissionTwo)
        And("has user") {
            coEvery { homePresenter.getUser() } returns user
            When("load and loadNextPage") {
                Then("first page result should be appended") {
                    val vm = HomeViewModel(homePresenter)
                    vm.observeStateAndEvents { stateObserver, _ ->
                        vm.load()
                        vm.loadNextPage()

                        val expectedStateAfterLoad = HomeReady(
                            submissions = listOf(submissionOne),
                            selectedSubreddit = defaultSubreddit,
                            subreddits = listOf(defaultSubreddit, nonDefaultSubreddit),
                            user = user
                        )

                        stateObserver.observed shouldContainAll listOf(
                            expectedStateAfterLoad,
                            expectedStateAfterLoad.copy(
                                submissions = listOf(submissionOne, submissionTwo)
                            )
                        )
                    }

                }
            }
            When("load and switchToUserlessMode") {
                val vm = HomeViewModel(homePresenter)
                vm.load()
                vm.switchToUserlessMode()
                Then("should logout") {
                    coVerify { homePresenter.logout() }
                }
                Then("should get userless mode credentials") {
                    coVerify { homePresenter.acquireUserlessCredentials() }
                }
                Then("should download subreddits and submissions again") {
                    coVerify(exactly = 2) { homePresenter.getSubreddits() }
                    coVerify(exactly = 2) { homePresenter.getSubredditPage(defaultSubreddit.queryName) }
                }
                Then("user should not be set") {
                    val vm = HomeViewModel(homePresenter)
                    vm.observeStateAndEvents { stateObserver, _ ->
                        vm.load()
                        vm.switchToUserlessMode()

                        stateObserver.observed.last() shouldBe HomeReady(
                            submissions = listOf(submissionOne),
                            selectedSubreddit = defaultSubreddit,
                            subreddits = listOf(defaultSubreddit, nonDefaultSubreddit),
                            user = null
                        )
                    }
                }
            }
        }
    }
    Given("default subreddit has submissionToVote at first page") {
        val submissionToVote = getSubmission(id = "voted id", fullname = "voted fullname")
        coEvery { homePresenter.getDefaultSubreddit() } returns defaultSubreddit
        coEvery { homePresenter.getSubreddits() } returns listOf(defaultSubreddit)
        coEvery { homePresenter.getSubredditPage(defaultSubreddit.queryName) } returns listOf(submissionToVote)
        coEvery { homePresenter.getUser() } returns user
        When("load and vote successfully") {
            coEvery { homePresenter.vote(fullname = submissionToVote.fullname, likes = true) } returns true
            val vm = HomeViewModel(homePresenter)

            vm.load()
            vm.vote(fullname = submissionToVote.fullname, liked = true)
            Then("submission's liked value should be updated") {
                submissionToVote.liked shouldBe true
            }
            Then("voting status should be set while voting") {
                val vm = HomeViewModel(homePresenter)
                vm.observeStateAndEvents { stateObserver, _ ->
                    vm.load()
                    vm.vote(fullname = submissionToVote.fullname, liked = true)

                    val expectedStateAfterLoad = HomeReady(
                        submissions = listOf(submissionToVote),
                        selectedSubreddit = defaultSubreddit,
                        subreddits = listOf(defaultSubreddit),
                        user = user,
                        voting = false
                    )

                    stateObserver.observed shouldContainAll listOf(
                        expectedStateAfterLoad.copy(voting = true),
                        expectedStateAfterLoad.copy(voting = false)
                    )
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
