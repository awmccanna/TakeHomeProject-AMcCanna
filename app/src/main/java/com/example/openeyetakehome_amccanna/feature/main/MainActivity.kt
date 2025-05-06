package com.example.openeyetakehome_amccanna.feature.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.openeyetakehome_amccanna.R
import com.example.openeyetakehome_amccanna.core.repository.PostRepository
import com.example.openeyetakehome_amccanna.feature.main.view_model.PostViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnPreMadePosts: Button = findViewById(R.id.btnPreMadePosts)
        val btnCustomPosts: Button = findViewById(R.id.btnCustomPosts)
        val btnRandomPost: Button = findViewById(R.id.btnRandomPost)
        val postRepository: PostRepository by inject()

        btnPreMadePosts.setOnClickListener {
            val intent = Intent(this, PreMadePostsActivity::class.java)
            startActivity(intent)
        }

        btnCustomPosts.setOnClickListener {
            val intent = Intent(this, CustomPostsActivity::class.java)
            startActivity(intent)
        }

        btnRandomPost.setOnClickListener {
            lifecycleScope.launch {
                displayRandomPost()
            }
        }

        lifecycleScope.launch {
            postRepository.loadPreMadePostsIfNeeded()
        }
    }

    private suspend fun displayRandomPost() {
        val postViewModel: PostViewModel by viewModel()
        val count = postViewModel.premadePostCount()
        if (count > 0) {
            val randomPostId = Random.nextInt(1, count)
            val post = postViewModel.getPostById(randomPostId)

            val builder = AlertDialog.Builder(this)
            builder.setTitle(post?.title ?: "-")
            builder.setMessage(post?.body ?: "-")
            builder.setPositiveButton("Close") { dialog, _ ->
                dialog.dismiss()
            }

            builder.show()
        } else {
            Log.i("displayRandomPost", "count is null or 0")
        }
    }
}