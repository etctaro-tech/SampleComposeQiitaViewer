package com.example.samplecomposeqiitaviewer

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import com.example.samplecomposeqiitaviewer.ui.SampleComposeQiitaViewerTheme
import junit.framework.Assert.fail
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @OptIn(ExperimentalTestApi::class)
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
        composeTestRule.onNodeWithContentDescription("Refresh Button").performClick()

        // とりあえず少し待つ（本来は通信の終了を待って次の処理とすべきです。）
        Thread.sleep(5000)

        // Nodeのツリーを再び出力
        composeTestRule.onRoot().printToLog("ForComposeUITest")

        // Columnを探す→子が20個あるはず
        // LazyColumnの場合は20個の要素が取得できる想定でも実際には表示範囲のみのリストとなる。
        // 例えば10個取得できても画面に10個まで表示できる場合は10個の要素のリスト。
        // →要素数が動的になるため、個数は直接は利用はできない。
        // composeTestRule.onNodeWithContentDescription("Item List").onChildren().assertCountEquals(20)

        // 20番目の要素のところまでスクロールでき、21番目にはスクロールできなければ、20個の要素が取得できていることになるためOK。
        // performScrollToIndexは1.0.0-beta06で追加されたAPI（参考： https://developer.android.com/jetpack/androidx/releases/compose-ui#1.0.0-beta06）
        composeTestRule.onNodeWithContentDescription("Item List").performScrollToIndex(19)

        try {
            composeTestRule.onNodeWithContentDescription("Item List").performScrollToIndex(20)
        } catch (e: Exception) {
            return
        }
        fail("21個以上の要素があるためNGとする。")
    }
}