package com.example.openeyetakehome_amccanna.core.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.openeyetakehome_amccanna.core.model.Post

@Dao
interface PostDao {
    @Insert
    suspend fun insert(post: Post)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<Post>)

    @Update
    suspend fun update(post: Post)

    @Delete
    suspend fun delete(post: Post)

    @Query("Delete from posts")
    suspend fun deleteAll()

    @Query("Select * from posts where id = :id")
    fun getPostLive(id: Int): LiveData<Post>

    @Query("Select * from posts")
    suspend fun getAll(): List<Post>

    @Query("Select * from posts where is_custom = :isCustom")
    suspend fun getAllWhereCustom(isCustom: Boolean): List<Post>
}