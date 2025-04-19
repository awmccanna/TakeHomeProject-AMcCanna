package com.example.openeyetakehome_amccanna.feature.main.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openeyetakehome_amccanna.core.model.Post
import com.example.openeyetakehome_amccanna.core.repository.PostRepository
import kotlinx.coroutines.launch

class PostViewModel(private val repository: PostRepository) : ViewModel() {
    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> = _posts

    fun loadPreMadePosts() {
        viewModelScope.launch {
            _posts.value = repository.getAllWhereCustom(false)
        }
    }

    fun savePost(post: Post) {
        viewModelScope.launch {
            repository.updatePost(post)
        }
    }

    fun deletePost(post: Post) {
        viewModelScope.launch {
            repository.deletePost(post)
        }
    }
}