package com.example.openeyetakehome_amccanna.feature.main.view_model

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openeyetakehome_amccanna.core.model.Post
import com.example.openeyetakehome_amccanna.core.repository.PostRepositoryInterface
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class PostViewModel(private val repository: PostRepositoryInterface) : ViewModel() {
    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts

    fun loadPreMadePosts() {
        viewModelScope.launch {
            _posts.value = repository.getAllWhereCustom(false)
        }
    }

    fun loadCustomPosts() {
        viewModelScope.launch {
            _posts.value = repository.getAllWhereCustom(true)
        }
    }

    fun createPost(post: Post) {
        viewModelScope.launch {
            val now = currentTimeString()
            val newPost = post.copy(createdAt = now, updatedAt = now)
            repository.insertPost(newPost)

            _posts.value = repository.getAllWhereCustom(post.isCustom)
        }
    }

    fun updatePost(post: Post) {
        viewModelScope.launch {
            val updatedPost = post.copy(updatedAt = currentTimeString())
            repository.updatePost(updatedPost)

            _posts.value = repository.getAllWhereCustom(post.isCustom)
        }
    }

    fun deletePost(post: Post) {
        viewModelScope.launch {
            repository.deletePost(post)
            _posts.value = repository.getAllWhereCustom(post.isCustom)
        }
    }

    suspend fun getPostById(id: Int): Post? {
        return repository.getPostById(id)
    }

    fun getLivePostById(id: Int): LiveData<Post> {
        return repository.getLivePostById(id)
    }

    private fun currentTimeString(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        } else {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            sdf.format(Date())
        }
    }
}