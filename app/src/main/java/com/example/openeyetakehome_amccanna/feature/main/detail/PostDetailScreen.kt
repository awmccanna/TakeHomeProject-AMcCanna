package com.example.openeyetakehome_amccanna.feature.main.detail

import android.app.Activity
import android.app.AlertDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.openeyetakehome_amccanna.core.model.Post
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.openeyetakehome_amccanna.feature.main.view_model.PostViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(post: Post?, postViewModel: PostViewModel) {
    val context = LocalContext.current
    val activity = context as? Activity

    var isEditing by remember { mutableStateOf(false) }
    var activeTitle by remember { mutableStateOf(post?.title ?: "") }
    var activeBody by remember { mutableStateOf(post?.body ?: "") }
    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                isEditing = !isEditing
            }) {
                if (isEditing) Icon(Icons.Default.Close, contentDescription = "Exit Editing")
                else Icon(Icons.Default.Edit, contentDescription = "Edit Post")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (isEditing) {
                TextField(
                    value = activeTitle,
                    onValueChange = { activeTitle = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = activeBody,
                    onValueChange = { activeBody = it },
                    label = { Text("Body") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row {
                    IconButton(
                        onClick = {
                            showDeleteDialog = true
                        }
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete Post")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = {
                        post?.let {
                            val updatedPost = it.copy(title = activeTitle, body = activeBody)
                            postViewModel.savePost(it.copy(title = activeTitle, body = activeBody))

                            activeTitle = updatedPost.title ?: ""
                            activeBody = updatedPost.body ?: ""

                            activity?.setResult(Activity.RESULT_OK)
                        }

                        isEditing = !isEditing
                    }) {
                        Text("Save changes")
                    }
                }
            } else {
                Text(
                    text = activeTitle,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(text = activeBody ?: "", style = MaterialTheme.typography.bodyLarge)
            }
        }
        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text(text = "Delete Post") },
                text = { Text("Are you sure you want to delete this post? It can't be undone.") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            post?.let {
                                postViewModel.deletePost(it)
                                activity?.setResult(Activity.RESULT_OK)
                                activity?.finish()
                            }
                            showDeleteDialog = false
                        }
                    ) {
                        Text("Delete", color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}