package com.example.openeyetakehome_amccanna.feature.main.detail


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.openeyetakehome_amccanna.core.ui.theme.OpeneyeTakeHomeAMcCannaTheme
import com.example.openeyetakehome_amccanna.feature.main.view_model.PostViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class PostDetailActivity : ComponentActivity() {
    private val postViewModel: PostViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val postId = intent.getIntExtra("post_id", -1)


        setContent {
            OpeneyeTakeHomeAMcCannaTheme {
                PostDetailScreen(postId = postId, postViewModel = postViewModel)
            }
        }
    }
}