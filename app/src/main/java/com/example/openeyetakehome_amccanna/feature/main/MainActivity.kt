package com.example.openeyetakehome_amccanna.feature.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.example.openeyetakehome_amccanna.R
import com.example.openeyetakehome_amccanna.core.repository.PostRepository
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnPreMadePosts: Button = findViewById(R.id.btnPreMadePosts)
        val btnCustomPosts: Button = findViewById(R.id.btnCustomPosts)
        val postRepository: PostRepository by inject()

        btnPreMadePosts.setOnClickListener {
            val intent = Intent(this, PreMadePostsActivity::class.java)
            startActivity(intent)
        }

        btnCustomPosts.setOnClickListener {
            val intent = Intent(this, CustomPostsActivity::class.java)
            startActivity(intent)
        }

        lifecycleScope.launch {
            postRepository.loadPreMadePostsIfNeeded()
        }
    }
}