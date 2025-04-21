package com.example.openeyetakehome_amccanna

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.openeyetakehome_amccanna.feature.main.CustomPostsActivity
import com.example.openeyetakehome_amccanna.feature.main.create.PostCreateActivity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CustomPostsActivityTest {

    @Before
    fun setUp() {
        Intents.init()
        ActivityScenario.launch(CustomPostsActivity::class.java)
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun emptyStateMessage_isVisible_whenNoPostsAvailable() {
        onView(withId(R.id.textEmptyState))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.no_custom_posts_available)))
    }

    @Test
    fun fabCreatePost_opensPostCreateActivity() {
        onView(withId(R.id.fabCreatePost)).perform(click())
        intended(hasComponent(PostCreateActivity::class.java.name))
    }
}