package com.thiosin.novus.domain.interactor

import com.thiosin.novus.data.network.NetworkDataSource
import com.thiosin.novus.data.prefs.UserInfoProvider
import com.thiosin.novus.kotest.MockKAwareBehaviorSpec
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk

class UserInteractorTest : MockKAwareBehaviorSpec({

    val networkDataSource: NetworkDataSource = mockk()
    val userInfoProvider: UserInfoProvider = mockk()
    val authInteractor: AuthInteractor = mockk()
    val userInteractor = UserInteractor(networkDataSource, userInfoProvider, authInteractor)

    Given("network") {
        coEvery { networkDataSource.vote(any(), any()) } returns true
        listOf(
            false to -1,
            null to 0,
            true to 1
        ).forEach { (liked, direction) ->
            When("vote with liked=$liked") {
                userInteractor.vote("id", liked)
                Then("direction should be $direction") {
                    coVerify { networkDataSource.vote("id", direction) }
                }
            }
        }
    }
})
