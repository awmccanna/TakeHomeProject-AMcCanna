package com.example.openeyetakehome_amccanna.core.repository

import androidx.lifecycle.LiveData
import com.example.openeyetakehome_amccanna.core.model.Post

interface PostRepositoryInterface {
    suspend fun all(): List<Post>
    fun getLivePostById(id: Int): LiveData<Post>
    suspend fun getPostById(id: Int): Post?
    suspend fun getAllWhereCustom(isCustom: Boolean): List<Post>
    suspend fun insertPost(post: Post)
    suspend fun insertPosts(posts: List<Post>)
    suspend fun updatePost(post: Post)
    suspend fun deletePost(post: Post)
    suspend fun deleteAll()
    suspend fun loadPreMadePostsIfNeeded(forceReload: Boolean = false)
}
