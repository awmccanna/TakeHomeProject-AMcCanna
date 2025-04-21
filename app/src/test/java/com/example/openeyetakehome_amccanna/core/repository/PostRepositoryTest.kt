package com.example.openeyetakehome_amccanna.core.repository

import com.example.openeyetakehome_amccanna.FakePostDao
import com.example.openeyetakehome_amccanna.FakeSharedPreferences
import com.example.openeyetakehome_amccanna.core.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PostRepositoryTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: PostRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = PostRepository(FakePostDao(), FakeSharedPreferences())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun insertPost() = runTest {
        val post = Post(id = 1, title = "Test", body = "Body", isCustom = true)
        repository.insertPost(post)

        val all = repository.all()
        assertTrue(all.contains(post))
    }

    @Test
    fun updatePost() = runTest {
        val post = Post(id = 1, title = "Original", body = "Body", isCustom = true)
        repository.insertPost(post)

        val updated = post.copy(title = "Updated")
        repository.updatePost(updated)

        val result = repository.getPostById(1)
        assertEquals("Updated", result?.title)
    }

    @Test
    fun deletePost() = runTest {
        val post = Post(id = 1, title = "Delete Me", body = "Bye", isCustom = true)
        repository.insertPost(post)
        repository.deletePost(post)

        val result = repository.getPostById(1)
        assertNull(result)
    }

    @Test
    fun getAllWhereCustom() = runTest {
        val customPost = Post(id = 1, title = "Custom", body = "", isCustom = true)
        val premadePost = Post(id = 2, title = "Premade", body = "", isCustom = false)

        repository.insertPosts(listOf(customPost, premadePost))

        val customPosts = repository.getAllWhereCustom(true)
        assertEquals(1, customPosts.size)
        assertEquals("Custom", customPosts.first().title)

        val preMadePosts = repository.getAllWhereCustom(false)
        assertEquals(1, preMadePosts.size)
        assertEquals("Premade", preMadePosts.first().title)
    }
}