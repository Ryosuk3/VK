package com.example.vk.di

import com.example.vk.data.network.YoutubeApiService
import com.example.vk.domain.repository.VideoRepository
import com.example.vk.ui.screens.videolist.VideoListViewModel
import okhttp3.OkHttpClient
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

val repositoryModule = module {
    single { VideoRepository(get()) }
}

val viewModelModule = module {
    viewModel { VideoListViewModel(get()) }
}

val appModules = listOf(networkModule, repositoryModule, viewModelModule)