package com.example.openeyetakehome_amccanna.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.openeyetakehome_amccanna.core.database.AppDatabase
import com.example.openeyetakehome_amccanna.core.network.ApiClient
import com.example.openeyetakehome_amccanna.core.repository.PostRepository
import com.example.openeyetakehome_amccanna.core.repository.PostRepositoryInterface
import com.example.openeyetakehome_amccanna.feature.main.view_model.PostViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { ApiClient() }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    single { get<AppDatabase>().postDao() }

    single<SharedPreferences> {
        androidContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    single { PostRepository(get(), get()) } bind PostRepositoryInterface::class

    viewModel { PostViewModel(get()) }
}