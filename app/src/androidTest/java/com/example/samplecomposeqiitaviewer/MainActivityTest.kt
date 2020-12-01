package com.example.samplecomposeqiitaviewer

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import com.example.samplecomposeqiitaviewer.ui.SampleComposeQiitaViewerTheme
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun test_RefreshButton_tap_once() {
        composeTestRule.setContent {
            SampleComposeQiitaViewerTheme {
                HomeScreen()
            }
        }

        // Nodeのツリーを出力
        composeTestRule.onRoot().printToLog("ForComposeUITest")

        // FABをAccessibilityで探す→タップ
        composeTestRule.onNodeWithLabel("Refresh Button").performClick()

        // とりあえず少し待つ
        Thread.sleep(5000)

        // Nodeのツリーを再び出力
        composeTestRule.onRoot().printToLog("ForComposeUITest")

        // Columnを探す→子が20個あるはず
        composeTestRule.onNodeWithLabel("Item List").onChildren().assertCountEquals(20)
    }
}