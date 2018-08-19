package com.akarbowy.codewarsclient

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.akarbowy.codewarsclient.ui.search.view.SearchActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ToolbarSearchModeTest {

    @get:Rule
    var mActivityRule = ActivityTestRule<SearchActivity>(SearchActivity::class.java)

    @Before
    fun switchToSearchMode() {
        onView(ViewMatchers.withId(R.id.toolbar_layout_action)).perform(click())
    }

    @Test
    fun clickClearQuery_check_fieldIsEmpty() {
        val fieldView = onView(withId(R.id.field_query))
        fieldView.perform(click(), typeText("codewars"))
        onView(withId(R.id.button_field_cancel)).perform(click())
        fieldView.check(matches(withText("")))
    }

    @Test
    fun typeRandomQuery_check_stateEmptyFilter() {
        onView(withId(R.id.field_query)).perform(typeText("2348dsj23nkfsd02j"))
        onView(withId(R.id.state_results_searching)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        onView(withId(R.id.state_results_no_found)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }
}
