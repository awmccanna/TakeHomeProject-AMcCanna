package com.example.openeyetakehome_amccanna.feature.main.create

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.openeyetakehome_amccanna.core.ui.theme.OpeneyeTakeHomeAMcCannaTheme
import com.example.openeyetakehome_amccanna.feature.main.view_model.PostViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class PostCreateActivity : ComponentActivity() {
    private val postViewModel: PostViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            OpeneyeTakeHomeAMcCannaTheme {
                PostCreateScreen(postViewModel = postViewModel)
            }
        }
    }
}