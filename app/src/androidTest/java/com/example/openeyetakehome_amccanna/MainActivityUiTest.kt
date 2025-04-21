package com.example.openeyetakehome_amccanna

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.intent.Intents.init
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.release
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.openeyetakehome_amccanna.feature.main.CustomPostsActivity
import com.example.openeyetakehome_amccanna.feature.main.MainActivity
import com.example.openeyetakehome_amccanna.feature.main.PreMadePostsActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityUiTest {


    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        init()
    }

    @After
    fun tearDown() {
        release()
    }

    @Test
    fun welcomeText_isDisplayed() {
        onView(withId(R.id.welcomeText))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.welcome_text)))
    }

    @Test
    fun customPostsButton_isDisplayedAndClickable() {
        onView(withId(R.id.btnCustomPosts))
            .check(matches(isDisplayed()))
            .check(matches(isClickable()))
    }

    @Test
    fun preMadePostsButton_isDisplayedAndClickable() {
        onView(withId(R.id.btnPreMadePosts))
            .check(matches(isDisplayed()))
            .check(matches(isClickable()))
    }

    @Test
    fun clickingCustomPostsButton_performsAction() {
        onView(withId(R.id.btnCustomPosts)).perform(click())
        intended(hasComponent(CustomPostsActivity::class.java.name))
    }

    @Test
    fun clickingPreMadePostsButton_performsAction() {
        onView(withId(R.id.btnPreMadePosts)).perform(click())
        intended(hasComponent(PreMadePostsActivity::class.java.name))
    }
}