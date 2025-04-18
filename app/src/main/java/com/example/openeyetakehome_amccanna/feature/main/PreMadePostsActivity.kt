package com.example.openeyetakehome_amccanna.feature.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyetakehome_amccanna.R
import com.example.openeyetakehome_amccanna.core.network.ApiClient
import kotlinx.coroutines.launch

val client = ApiClient()

class PreMadePostsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PostRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.posts_premade)

        adapter = PostRecyclerViewAdapter(emptyList())

        recyclerView = findViewById(R.id.postRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            try {
                val posts = client.allPosts()
                adapter.setInitialData(posts)
            } catch (e: Exception) {
                Log.e("Exception", "${e.message}")
            }
        }
    }
}