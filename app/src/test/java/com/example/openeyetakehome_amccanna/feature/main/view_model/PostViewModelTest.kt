package com.example.openeyetakehome_amccanna.feature.main.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.openeyetakehome_amccanna.FakePostRepository
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
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PostViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var postViewModel: PostViewModel
    private lateinit var testRepository: FakePostRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        testRepository = FakePostRepository()
        postViewModel = PostViewModel(testRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getPosts() = runTest {
        val post1 = Post(id = 1, title = "A", body = "B", isCustom = true)
        val post2 = Post(id = 2, title = "C", body = "D", isCustom = true)
        postViewModel.createPost(post1)
        postViewModel.createPost(post2)

        postViewModel.loadCustomPosts()
        testDispatcher.scheduler.advanceUntilIdle()

        val posts = postViewModel.posts.value
        assertEquals(2, posts?.size)

        assertTrue(posts!!.any { it.id == post1.id })
        assertTrue(posts.any { it.id == post2.id })

    }

    @Test
    fun loadPreMadePosts() = runTest {
        val preMadePost = Post(id = 1, title = "Custom", body = "Body", isCustom = false)
        testRepository.insertPost(preMadePost)

        postViewModel.loadCustomPosts()
        testDispatcher.scheduler.advanceUntilIdle()

        val posts = postViewModel.posts.value
        assertTrue(posts!!.all { it.isCustom })
    }

    @Test
    fun loadCustomPosts() = runTest {
        val customPost = Post(id = 1, title = "Custom", body = "Body", isCustom = true)
        testRepository.insertPost(customPost)

        postViewModel.loadCustomPosts()
        testDispatcher.scheduler.advanceUntilIdle()

        val posts = postViewModel.posts.value
        assertTrue(posts!!.all { it.isCustom })
    }

    @Test
    fun createPost() = runTest {
        val newPost = Post(
            id = 1,
            title = "Test Title",
            body = "Test Body",
            isCustom = true,
        )

        postViewModel.createPost(newPost)

        testDispatcher.scheduler.advanceUntilIdle()

        val post = postViewModel.getPostById(1)
        assertEquals("Test Title", post?.title)
    }

    @Test
    fun updatePost() = runTest {
        val newPost = Post(
            id = 1,
            title = "Test Title",
            body = "Test Body",
            isCustom = true,
        )

        postViewModel.createPost(newPost)
        testDispatcher.scheduler.advanceUntilIdle()

        val updatedPost = newPost.copy(title = "Updated title", body = "Updated body text")

        postViewModel.updatePost(updatedPost)
        testDispatcher.scheduler.advanceUntilIdle()

        val post = postViewModel.getPostById(1)
        if (post != null) {
            assertEquals("Updated title", post.title)
            assertEquals("Updated body text", post.body)
            assertNotEquals(post.createdAt, post.updatedAt)
        }
    }

    @Test
    fun deletePost() = runTest {
        val postToDelete = Post(id = 1, title = "ToDelete", body = "This one", isCustom = true)

        postViewModel.createPost(postToDelete)
        testDispatcher.scheduler.advanceUntilIdle()

        postViewModel.deletePost(postToDelete)
        testDispatcher.scheduler.advanceUntilIdle()

        val posts = postViewModel.posts.value
        assertFalse(posts!!.contains(postToDelete))
    }
}