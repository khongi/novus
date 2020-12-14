package com.thiosin.novus.screens.home

import com.thiosin.novus.R
import com.thiosin.novus.domain.interactor.AuthInteractor
import com.thiosin.novus.domain.interactor.SubredditInteractor
import com.thiosin.novus.domain.interactor.UserInteractor
import com.thiosin.novus.domain.model.Subreddit
import com.thiosin.novus.domain.model.SubredditType
import com.thiosin.novus.domain.model.User
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk

class HomePresenterTest : BehaviorSpec({

    val authInteractor: AuthInteractor = mockk()
    val userInteractor: UserInteractor = mockk()
    val subredditInteractor: SubredditInteractor = mockk()
    val user = User("username")
    val builtInSubreddit = Subreddit(
        queryName = "builtIn queryName",
        displayName = "builtIn displayName",
        type = SubredditType.Frontpage,
    )
    val userCommunitySubreddit = Subreddit(
        queryName = "user queryName",
        displayName = "user displayName",
        type = SubredditType.Community,
    )
    val userlessCommunitySubreddit = Subreddit(
        queryName = "userless queryName",
        displayName = "userless displayName",
        type = SubredditType.Community,
    )
    val homePresenter = HomePresenter(authInteractor, subredditInteractor, userInteractor)

    Given("one subreddit for each type") {
        coEvery { subredditInteractor.getUserSubreddits() } returns listOf(userCommunitySubreddit)
        coEvery { subredditInteractor.getUserlessSubreddits() } returns listOf(userlessCommunitySubreddit)
        coEvery { subredditInteractor.getBuiltInSubreddits() } returns listOf(builtInSubreddit)
        And("there is no logged in user") {
            coEvery { userInteractor.getUser() } returns null
            When("getBuiltInSubreddits without user") {
                val result = homePresenter.getSubreddits()
                Then("result should contain built in and userless community subreddits in order") {
                    result.size shouldBe 2
                    result[0].type shouldBe SubredditType.Frontpage
                    result[1] shouldBe userlessCommunitySubreddit
                }
                Then("built in subreddit should be assigned icon resource") {
                    result[0].iconResource shouldBe R.drawable.ic_home
                }
            }
        }
        And("there is a user logged in") {
            coEvery { userInteractor.getUser() } returns user
            When("getBuiltInSubreddits with user") {
                val result = homePresenter.getSubreddits()
                Then("result should contain built in and user community subreddits in order") {
                    result.size shouldBe 2
                    result[0].type shouldBe SubredditType.Frontpage
                    result[1] shouldBe userCommunitySubreddit
                }
            }
        }
    }
}) {
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
