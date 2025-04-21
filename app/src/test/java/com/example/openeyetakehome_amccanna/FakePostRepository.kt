package com.example.openeyetakehome_amccanna

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.openeyetakehome_amccanna.core.model.Post
import com.example.openeyetakehome_amccanna.core.repository.PostRepositoryInterface

class FakePostRepository : PostRepositoryInterface {
    private val posts = mutableListOf<Post>()
    private val postLiveData = MutableLiveData<Post>()

    override suspend fun all(): List<Post> {
        return posts.toList()
    }

    override fun getLivePostById(id: Int): LiveData<Post> {
        val post = posts.find { it.id == id }
        postLiveData.value = post
        return postLiveData
    }

    override suspend fun getPostById(id: Int): Post? {
        return posts.find { it.id == id }
    }

    override suspend fun getAllWhereCustom(isCustom: Boolean): List<Post> {
        return posts.filter { it.isCustom == isCustom }
    }

    override suspend fun insertPost(post: Post) {
        // For 'replace if exists' functionality
        posts.removeAll { it.id == post.id }
        posts.add(post)
    }

    override suspend fun insertPosts(posts: List<Post>) {
        posts.forEach { insertPost(it) }
    }

    override suspend fun updatePost(post: Post) {
        val index = posts.indexOfFirst { it.id == post.id }

        if (index != -1) {
            posts[index] = post
        }
    }

    override suspend fun deletePost(post: Post) {
        posts.removeAll { it.id == post.id }
    }

    override suspend fun deleteAll() {
        posts.clear()
    }

    override suspend fun loadPreMadePostsIfNeeded(forceReload: Boolean) {
        if (posts.isEmpty() || forceReload) {
            val preMadePosts = listOf(
                Post(id = 1, title = "First title", body = "First post body text", isCustom = false),
                Post(id = 2, title = "Second title", body = "Second post body text", isCustom = false),
                Post(id = 3, title = "Third title", body = "Third post body text", isCustom = false)
            )

            posts.addAll(preMadePosts)
        }
    }
}