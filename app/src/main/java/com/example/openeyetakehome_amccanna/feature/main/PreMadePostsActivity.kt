package com.example.openeyetakehome_amccanna.feature.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyetakehome_amccanna.R
import com.example.openeyetakehome_amccanna.core.repository.PostRepository
import com.example.openeyetakehome_amccanna.feature.main.detail.PostDetailActivity
import com.example.openeyetakehome_amccanna.feature.main.view_model.PostViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class PreMadePostsActivity : AppCompatActivity() {
    private lateinit var adapter: PostRecyclerViewAdapter
    private val postViewModel: PostViewModel by viewModel()
    private val postEditLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            postViewModel.loadPreMadePosts()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts_premade)


        val fabRefresh: FloatingActionButton = findViewById(R.id.fabResetPosts)
        fabRefresh.setOnClickListener {
            resetConfirmationDialog()
        }


        adapter = PostRecyclerViewAdapter(emptyList()) { post ->
            val intent = Intent(this, PostDetailActivity::class.java).apply {
                putExtra("post", post)
            }

            postEditLauncher.launch(intent)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.postRecyclerView).apply {
            layoutManager = LinearLayoutManager(this@PreMadePostsActivity)
            this.adapter = this@PreMadePostsActivity.adapter
        }

        postViewModel.posts.observe(this) { posts ->
            adapter.setInitialData(posts)
        }

        postViewModel.loadPreMadePosts()
    }

    private fun resetConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Reset Pre-Made Posts")
        builder.setMessage("This will erase any changes that have been made to the pre-made posts. Do you want to continue?")
        builder.setPositiveButton("Yes") { dialog, which ->
            resetData()
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }

        builder.create().show()
    }

    private fun resetData() {
        lifecycleScope.launch {
            try {
                val postRepository: PostRepository by inject()
                postRepository.deleteAll()
                postRepository.loadPreMadePostsIfNeeded(true)
                postViewModel.loadPreMadePosts()
            } catch (e: Exception) {
                Log.e("PreMadePostsActivity", "${e.message}")
            }
        }
    }
}