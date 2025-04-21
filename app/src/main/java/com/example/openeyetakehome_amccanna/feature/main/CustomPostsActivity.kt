package com.example.openeyetakehome_amccanna.feature.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyetakehome_amccanna.R
import com.example.openeyetakehome_amccanna.feature.main.create.PostCreateActivity
import com.example.openeyetakehome_amccanna.feature.main.detail.PostDetailActivity
import com.example.openeyetakehome_amccanna.feature.main.view_model.PostViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.androidx.viewmodel.ext.android.viewModel

class CustomPostsActivity: AppCompatActivity() {
    private lateinit var adapter: PostRecyclerViewAdapter
    private val postViewModel: PostViewModel by viewModel()
    private val postDetailLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            postViewModel.loadCustomPosts()
        }
    }

    private val postCreateLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            postViewModel.loadCustomPosts()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts_custom)

        val emptyStateTextView = findViewById<TextView>(R.id.textEmptyState)
        val fabNewPost: FloatingActionButton = findViewById(R.id.fabCreatePost)
        fabNewPost.setOnClickListener {
            val intent = Intent(this, PostCreateActivity::class.java).apply {}

            postCreateLauncher.launch(intent)
        }

        adapter = PostRecyclerViewAdapter(emptyList()) { post ->
            val intent = Intent(this, PostDetailActivity::class.java).apply {
                putExtra("post_id", post.id)
            }

            postDetailLauncher.launch(intent)
        }

        findViewById<RecyclerView>(R.id.postRecyclerView).apply {
            layoutManager = LinearLayoutManager(this@CustomPostsActivity)
            this.adapter = this@CustomPostsActivity.adapter
        }

        postViewModel.posts.observe(this) { posts ->
            if (posts.isEmpty()) {
                emptyStateTextView.visibility = View.VISIBLE
            } else {
                emptyStateTextView.visibility = View.GONE
            }
            adapter.setInitialData(posts)
        }

        postViewModel.loadCustomPosts()
    }
}