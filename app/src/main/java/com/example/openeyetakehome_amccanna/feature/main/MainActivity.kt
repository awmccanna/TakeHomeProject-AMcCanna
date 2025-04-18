package com.example.openeyetakehome_amccanna.feature.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyetakehome_amccanna.R
import com.example.openeyetakehome_amccanna.core.network.ApiClient
import com.example.openeyetakehome_amccanna.core.ui.theme.OpeneyeTakeHomeAMcCannaTheme
import com.example.openeyetakehome_amccanna.core.model.Post
import com.example.openeyetakehome_amccanna.core.network.model.PostDto
import com.example.openeyetakehome_amccanna.core.network.model.toPost
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnPreMadePosts: Button = findViewById(R.id.btnPreMadePosts)
        val btnCustomPosts: Button = findViewById(R.id.btnCustomPosts)

        btnPreMadePosts.setOnClickListener {
            val intent = Intent(this, PreMadePostsActivity::class.java)
            startActivity(intent)
        }


        /*
        enableEdgeToEdge()
        setContent {
            OpeneyeTakeHomeAMcCannaTheme(
                dynamicColor = false
            ) {
                var postList by remember { mutableStateOf(emptyList<Post>()) }

                LaunchedEffect(Unit) {
                    try {
                        postList = client.allPosts()
                    } catch (e: Exception) {
                        Log.e("Exception", "${e.message}")
                    }
                }

                BuildScaffold(postList)
            }
        }
         */
    }
}

@Composable
fun Posts(posts: List<Post>, paddingValues: PaddingValues) {
    if (posts.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
        ) {
            items(posts.size) { index ->
                val post = posts[index]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = "${post.id}. ${post.title}",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        ElevatedCard(
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                            ),
                            modifier = Modifier.padding(6.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                            ),
                        ) {
                            Text(
                                text = post.body ?: "",
                                modifier = Modifier.padding(12.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    } else {
        Text(text = "Loading")
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuildScaffold(posts: List<Post>) {
    var presses by remember { mutableIntStateOf(0) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                title = {
                    Text("Take home test")
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ) {}
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { presses++ }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {
        Posts(posts = posts, it)
    }
}