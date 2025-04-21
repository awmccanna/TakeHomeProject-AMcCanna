package com.example.openeyetakehome_amccanna.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.openeyetakehome_amccanna.core.dao.PostDao
import com.example.openeyetakehome_amccanna.core.model.Post

@Database(entities = [Post::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}