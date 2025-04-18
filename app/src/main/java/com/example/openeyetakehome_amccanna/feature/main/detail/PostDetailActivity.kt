package com.example.openeyetakehome_amccanna.feature.main.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.openeyetakehome_amccanna.core.ui.theme.OpeneyeTakeHomeAMcCannaTheme


class PostDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val postId = intent.getIntExtra("postId", -1)

        setContent {
            OpeneyeTakeHomeAMcCannaTheme {
                PostDetailScreen(postId)
            }
        }
    }
}