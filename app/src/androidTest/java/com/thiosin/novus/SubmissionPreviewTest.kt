package com.thiosin.novus

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.thiosin.novus.screens.home.HomeScreen
import com.thiosin.novus.screens.home.HomeScreenTestTag
import com.thiosin.novus.ui.theme.NovusTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SubmissionPreviewTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var activity: ComponentActivity

    @Before
    fun setUp() {
        composeTestRule.activityRule.scenario.onActivity { newActivity ->
            activity = newActivity

            composeTestRule.setContent {
                NovusTheme {
                    HomeScreen(
                        viewState = homeReadyState,
                        onNextPage = { /*TODO*/ },
                        onSwitchSubreddit = { /*TODO*/ },
                        onLinkClick = { /*TODO*/ },
                        onDetailsClick = { /*TODO*/ },
                        onLoginClick = { /*TODO*/ },
                        onLogoutClick = { /*TODO*/ },
                        onVoteClick = { _, _ -> /*TODO*/ }
                    )
                }
            }
        }
    }

    @Test
    fun app_launches() {
        composeTestRule.onNodeWithTag(HomeScreenTestTag).assertIsDisplayed()
    }

}