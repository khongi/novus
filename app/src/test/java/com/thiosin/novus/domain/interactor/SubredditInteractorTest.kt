package com.thiosin.novus.domain.interactor

import com.thiosin.novus.data.network.NetworkDataSource
import com.thiosin.novus.domain.model.SubredditType
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.collections.shouldExist
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.mockk

class SubredditInteractorTest : BehaviorSpec({

    val networkDataSource: NetworkDataSource = mockk()
    val subredditInteractor = SubredditInteractor(networkDataSource)

    Given("no setup") {
        When("getBuiltInSubreddits") {
            val result = subredditInteractor.getBuiltInSubreddits()
            Then("first in the list should be the default subreddit") {
                result shouldHaveAtLeastSize 1
                result[0].type shouldBe SubredditType.Frontpage
            }
            Then("list should contain All and Popular") {
                result shouldExist { it.type == SubredditType.All }
                result shouldExist { it.type == SubredditType.Popular }
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
