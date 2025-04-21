package com.example.openeyetakehome_amccanna

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.openeyetakehome_amccanna.core.dao.PostDao
import com.example.openeyetakehome_amccanna.core.model.Post

class FakePostDao : PostDao {
    private val daoPosts = mutableListOf<Post>()

    override suspend fun insert(post: Post) {
        daoPosts.removeAll { it.id == post.id }
        daoPosts.add(post)
    }

    override suspend fun insertAll(posts: List<Post>) {
        daoPosts.removeAll { post -> posts.any { it.id == post.id } }
        daoPosts.addAll(posts)
    }

    override suspend fun update(post: Post) {
        daoPosts.replaceAll { if (it.id == post.id) post else it }
    }

    override suspend fun delete(post: Post) {
        daoPosts.removeIf { it.id == post.id }
    }

    override suspend fun deleteAll() {
        daoPosts.clear()
    }

    override fun getPostById(id: Int): Post? {
        return daoPosts.find { it.id == id }
    }

    override fun getLivePostById(id: Int): LiveData<Post> {
        val result = daoPosts.find { it.id == id }
        return MutableLiveData(result)
    }

    override suspend fun getAll(): List<Post> = daoPosts

    override suspend fun getAllWhereCustom(isCustom: Boolean): List<Post> {
        return daoPosts.filter { it.isCustom == isCustom }
    }
}