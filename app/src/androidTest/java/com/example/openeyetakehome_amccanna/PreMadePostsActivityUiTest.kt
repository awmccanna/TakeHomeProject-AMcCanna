package com.example.openeyetakehome_amccanna

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.openeyetakehome_amccanna.feature.main.PreMadePostsActivity
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PreMadePostsActivityUiTest {
    @Before
    fun setUp() {
        Intents.init()
        ActivityScenario.launch(PreMadePostsActivity::class.java)
    }

    @After
    fun tearDown() {
        Intents.release()
    }
}