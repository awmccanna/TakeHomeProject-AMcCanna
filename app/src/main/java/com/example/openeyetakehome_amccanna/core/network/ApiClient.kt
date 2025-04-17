package com.example.openeyetakehome_amccanna.core.network

import android.util.Log
import com.example.openeyetakehome_amccanna.core.model.Post
import com.example.openeyetakehome_amccanna.core.network.model.PostDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject

class ApiClient {
    private val client: OkHttpClient = OkHttpClient.Builder()
        .build()
    private val basePostUrl = "https://jsonplaceholder.typicode.com"

    suspend fun getPost(postId: Int = 1): PostDto {
        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder()
                    .url(basePostUrl + "/posts/${postId}")
                    .build()

                val response: Response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val responseData = response.body?.string()
                    if (responseData.isNullOrEmpty()) {
                        throw Exception("Empty response")
                    }

                    val data = JSONObject(responseData)

                    val userId = data.optInt("userId", -1)
                    val id = data.optInt("id", -1)
                    val title = data.optString("title", "")
                    val body = data.optString("body", "")

                    if (userId == -1 || id == -1) {
                        throw Exception("userId or id not found in response")
                    }

                    return@withContext PostDto(
                        userId,
                        id,
                        title,
                        body,
                    )
                } else {
                    throw Exception("Data not returned from api call")
                }
            } catch (e: Exception) {
                Log.e("ApiClient", "${e.message}, ${e.printStackTrace()}")
                throw e
            }

        }
    }

    suspend fun allPosts(): List<Post> {
        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder()
                    .url("$basePostUrl/posts")
                    .build()

                val response: Response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val responseData = response.body?.string()
                    if (responseData.isNullOrEmpty()) {
                        throw Exception("Empty response")
                    }

                    val postList = mutableListOf<Post>()

                    val data = JSONArray(responseData)

                    for (i in 0 until data.length()) {
                        val item: JSONObject = data.getJSONObject(i)

                        val id = item.optInt("id", -1)
                        val title = item.optString("title", "")
                        val body = item.optString("body", "")

                        if (id != -1) {
                            postList.add(Post(id, title, body))
                        }
                    }

                    return@withContext postList
                } else {
                    throw Exception("Data not returned from api call")
                }
            } catch (e: Exception) {
                Log.e("ApiClient", "${e.message}, ${e.printStackTrace()}")
                throw e
            }
        }
    }
}