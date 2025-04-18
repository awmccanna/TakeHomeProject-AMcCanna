package com.example.openeyetakehome_amccanna.feature.main.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.openeyetakehome_amccanna.core.model.Post
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun PostDetailScreen(postId: Int) {
    var isEditing by remember { mutableStateOf(false) }
    val mockPost = remember {
        Post(id = postId, title = "Temp Title", body = "Temp body of text")
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(Icons.Default.Edit, contentDescription = "Add")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = mockPost.title ?: "",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(text = mockPost.body ?: "", style = MaterialTheme.typography.bodyLarge)

        }
    }
}