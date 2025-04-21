package com.example.openeyetakehome_amccanna.core.repository

import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import com.example.openeyetakehome_amccanna.core.dao.PostDao
import com.example.openeyetakehome_amccanna.core.model.Post
import com.example.openeyetakehome_amccanna.core.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class PostRepository(
    private val postDao: PostDao,
    private val sharedPreferences: SharedPreferences,
) : PostRepositoryInterface {
    private val client = ApiClient()

    private fun currentTimeString(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        } else {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            sdf.format(Date())
        }
    }

    override suspend fun all(): List<Post> = withContext(Dispatchers.IO) {
        postDao.getAll()
    }

    override fun getLivePostById(id: Int): LiveData<Post> {
        return postDao.getLivePostById(id)
    }

    override suspend fun getPostById(id: Int): Post? {
        return postDao.getPostById(id)
    }

    override suspend fun getAllWhereCustom(isCustom: Boolean): List<Post> = withContext(Dispatchers.IO) {
        postDao.getAllWhereCustom(isCustom)
    }

    override suspend fun insertPost(post: Post) = withContext(Dispatchers.IO) {
        postDao.insert(post)
    }

    override suspend fun insertPosts(posts: List<Post>) = withContext(Dispatchers.IO) {
        val now = currentTimeString()
        val withTimeStamps = posts.map { post ->
            post.copy(createdAt = now, updatedAt = now)
        }
        postDao.insertAll(withTimeStamps)
    }

    override suspend fun updatePost(post: Post) = withContext(Dispatchers.IO) {
        postDao.update(post)
    }

    override suspend fun deletePost(post: Post) = withContext(Dispatchers.IO) {
        postDao.delete(post)
    }

    override suspend fun deleteAll() = withContext(Dispatchers.IO) {
        postDao.deleteAll()
    }

    override suspend fun loadPreMadePostsIfNeeded(forceReload: Boolean) {
        withContext(Dispatchers.IO) {
            val hasLoadedPosts = sharedPreferences.getBoolean("has_premade_posts", false)

            if (!hasLoadedPosts || forceReload) {
                try {
                    val posts = client.allPosts()
                    insertPosts(posts)

                    sharedPreferences.edit() { putBoolean("has_premade_posts", true) }
                    Log.d("loadInitialPostsIfNeeded", "Pre-made posts")
                } catch (e: Exception) {
                    Log.e("loadInitialPostsIfNeeded", "${e.message}")
                }
            }
        }
    }
}
