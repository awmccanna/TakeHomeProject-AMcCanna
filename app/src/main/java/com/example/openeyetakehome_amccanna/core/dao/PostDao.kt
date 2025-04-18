package com.example.openeyetakehome_amccanna.core.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.openeyetakehome_amccanna.core.model.Post

@Dao
interface PostDao {
    @Insert
    suspend fun insert(post: Post)

    @Update
    suspend fun update(post: Post)

    @Delete
    suspend fun delete(post: Post)

    @Query("Select * from posts where id = :id")
    suspend fun getPost(id: Long): Post?

    @Query("Select * from posts")
    suspend fun getAll(): List<Post>
}