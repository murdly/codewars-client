package com.akarbowy.codewarsclient

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.akarbowy.codewarsclient.ui.search.view.SearchActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ToolbarNormalModeTest {

    @get:Rule
    var searchActivityActivityTestRule = ActivityTestRule<SearchActivity>(SearchActivity::class.java)

    @Test
    fun clickToolbarNormal_check_toolbarSwitchedToSearchMode() {
        onView(withId(R.id.toolbar_layout_action)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.toolbar_layout_action)).perform(click())
        onView(withId(R.id.toolbar_layout_action)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        onView(withId(R.id.toolbar_layout_searchable)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }
}
