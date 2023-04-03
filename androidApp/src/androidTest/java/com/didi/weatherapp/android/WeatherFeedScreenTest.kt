package com.didi.weatherapp.android

import androidx.activity.ComponentActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.hasScrollToNodeAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToNode
import com.didi.weatherapp.android.feed.WeatherFeedScreen
import com.didi.weatherapp.repository.fake.FakeWeatherAlertsRepository
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalMaterialApi::class)
class WeatherFeedScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun refreshing_shows_pull_to_refresh_Spinner() {
        composeTestRule.setContent {
            WeatherFeedScreen(emptyList(),true, {}, {})
        }

        composeTestRule
            .onNodeWithContentDescription("pull-to-refresh")
            .assertExists()
    }

    @Test
    fun feed_whenHasAlerts_showsAlerts() {
        composeTestRule.setContent {
            WeatherFeedScreen(FakeWeatherAlertsRepository.fakeAlerts,false, {}, {})
        }

        composeTestRule
            .onNodeWithText(
                FakeWeatherAlertsRepository.fakeAlerts[0].event,
                substring = true,
            )
            .assertExists()

        composeTestRule.onNode(hasScrollToNodeAction())
            .performScrollToNode(
                hasText(
                    FakeWeatherAlertsRepository.fakeAlerts[5].event,
                    substring = true,
                ),
            )

        composeTestRule
            .onNodeWithText(
                FakeWeatherAlertsRepository.fakeAlerts[5].event,
                substring = true,
            )
            .assertExists()
    }


}
