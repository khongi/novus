package com.thiosin.novus

import androidx.activity.ComponentActivity
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.milliseconds
import com.thiosin.novus.screens.home.HomeScreen
import com.thiosin.novus.screens.home.HomeScreenTestTag
import com.thiosin.novus.ui.theme.NovusTheme
import com.thiosin.novus.ui.view.AppBarTitleTestTag
import com.thiosin.novus.ui.view.DrawerTestTag
import com.thiosin.novus.ui.view.NavIconTestTag
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

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
                        onNextPage = { },
                        onSwitchSubreddit = { },
                        onLinkClick = { },
                        onDetailsClick = { },
                        onLoginClick = { },
                        onLogoutClick = { },
                        onVoteClick = { _, _ -> }
                    )
                }
            }
        }
    }

    @Test
    fun app_launches() {
        composeTestRule.onNodeWithTag(HomeScreenTestTag).assertIsDisplayed()
    }

    @Test
    fun navIconClick_drawerShows() {
        composeTestRule.onNodeWithTag(NavIconTestTag).performClick()
        composeTestRule.onNodeWithTag(DrawerTestTag).assertIsDisplayed()
    }

    @Test
    fun navIconClick_subredditEntriesAreShown() {
        composeTestRule.onNodeWithTag(NavIconTestTag).performClick()
        composeTestRule.onNodeWithText(builtInSubs[1].displayName).assertIsDisplayed()
        composeTestRule.onNodeWithText(communitySubs[1].displayName).assertIsDisplayed()
    }

    @Test
    fun title_isSelectedSubredditName() {
        composeTestRule.onNodeWithTag(AppBarTitleTestTag).assertTextEquals(builtInSubs[0].displayName)
    }

    @Test
    fun list_firstItemShows() {
        composeTestRule.onNodeWithText(submissions[0].title).assertExists()
    }

    @Test
    fun swipeRight_drawerShows() {
        composeTestRule.onNodeWithTag(HomeScreenTestTag).performGesture {
            this.swipe(
                start = this.center,
                end = Offset(this.center.x + 300, this.center.y),
                duration = 200.milliseconds
            )
        }
        composeTestRule.onNodeWithTag(DrawerTestTag).assertIsDisplayed()
    }
}