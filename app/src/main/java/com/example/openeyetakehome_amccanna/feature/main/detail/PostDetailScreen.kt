package com.example.openeyetakehome_amccanna.feature.main.detail

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.openeyetakehome_amccanna.feature.main.view_model.PostViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun PostDetailScreen(postId: Int, postViewModel: PostViewModel) {
    val context = LocalContext.current
    val activity = context as? Activity

    var showDeleteDialog by remember { mutableStateOf(false) }

    val livePost by postViewModel.getLivePostById(postId).observeAsState()
    var isEditing by remember { mutableStateOf(false) }
    var activeTitle by remember { mutableStateOf("") }
    var activeBody by remember { mutableStateOf("") }

    val gradient = Brush.linearGradient(
        colors = listOf(
            Color(0xB4FF9800),
            Color(0xFEB5FBFF)
        )
    )

    LaunchedEffect(livePost?.id) {
        livePost?.let {
            activeTitle = it.title ?: ""
            activeBody = it.body ?: ""
        }
    }

    if (livePost == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading Post")
        }
    } else {
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(gradient)
                    .padding(padding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
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
                                livePost?.let {
                                    val updatedPost =
                                        it.copy(title = activeTitle, body = activeBody)
                                    postViewModel.updatePost(
                                        it.copy(
                                            title = activeTitle,
                                            body = activeBody
                                        )
                                    )

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
                        livePost?.createdAt?.let {
                            Text(
                                text = "Created: ${formatDate(it)}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                        }

                        livePost?.let {
                            if (it.createdAt != it.updatedAt) {
                                Text(
                                    text = "Edited: ${formatDate(it.updatedAt)}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Text(
                            text = activeBody,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(top = 16.dp)
                        )
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
                                    livePost?.let {
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
    }
}

private fun formatDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM dd, yyyy - h:mm a", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        if (date != null) outputFormat.format(date) else ""
    } catch (e: Exception) {
        ""
    }
}