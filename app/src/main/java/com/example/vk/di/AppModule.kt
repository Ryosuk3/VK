package com.example.vk.di

import android.content.Context
import androidx.room.Room
import com.example.vk.data.local.AppDatabase
import com.example.vk.data.local.VideoDao
import com.example.vk.data.network.YoutubeApiService
import com.example.vk.domain.repository.VideoRepository
import com.example.vk.ui.screens.videolist.VideoListViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/youtube/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
            .create(YoutubeApiService::class.java)
    }
}

val databaseModule = module {
    single { provideDatabase(androidContext()) }
    single { provideVideoDao(get()) }
}

val repositoryModule = module {
    single { VideoRepository(get(), get()) }
}

val viewModelModule = module {
    viewModel { VideoListViewModel(get()) }
}

val appModules = listOf(networkModule, databaseModule, repositoryModule, viewModelModule)

fun provideDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "video_database"
    ).fallbackToDestructiveMigration() // Позволяет перегенерировать базу при изменениях
        .build()
}

fun provideVideoDao(db: AppDatabase): VideoDao {
    return db.videoDao()
}
