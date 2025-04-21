package com.example.openeyetakehome_amccanna.feature.main.create

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
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.openeyetakehome_amccanna.core.model.Post
import com.example.openeyetakehome_amccanna.feature.main.view_model.PostViewModel

@Composable
fun PostCreateScreen(postViewModel: PostViewModel) {
    val context = LocalContext.current
    val activity = context as? Activity

    var activeTitle by remember { mutableStateOf("") }
    var activeBody by remember { mutableStateOf("") }

    val gradient = Brush.linearGradient(
        colors = listOf(
            Color(0xB4FF9800),
            Color(0xFEB5FBFF)
        )
    )

    Scaffold { padding ->
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
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = {
                        activity?.setResult(Activity.RESULT_CANCELED)
                        activity?.finish()
                    }) {
                        Text("Cancel")
                    }
                    Button(onClick = {
                        postViewModel.createPost(
                            Post(
                                title = activeTitle,
                                body = activeBody,
                                isCustom = true,
                            )
                        )

                        activity?.setResult(Activity.RESULT_OK)
                        activity?.finish()
                    }) {
                        Text("Create")
                    }
                }
            }
        }
    }
}
