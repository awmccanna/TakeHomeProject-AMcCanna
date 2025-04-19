package com.example.openeyetakehome_amccanna.feature.main.detail

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.openeyetakehome_amccanna.core.model.Post
import com.example.openeyetakehome_amccanna.core.ui.theme.OpeneyeTakeHomeAMcCannaTheme
import com.example.openeyetakehome_amccanna.feature.main.view_model.PostViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class PostDetailActivity : ComponentActivity() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val postViewModel: PostViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        coroutineScope.launch {
            val post: Post? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("post", Post::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableExtra("post")
            }

            setContent {
                OpeneyeTakeHomeAMcCannaTheme {
                    PostDetailScreen(post, postViewModel)
                }
            }
        }
    }
}