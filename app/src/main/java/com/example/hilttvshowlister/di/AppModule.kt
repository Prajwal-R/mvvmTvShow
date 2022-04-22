package com.example.hilttvshowlister.di

import android.content.Context
import com.example.hilttvshowlister.dao.TvShowDao
import com.example.hilttvshowlister.model.TvShowDb
import com.example.hilttvshowlister.network.ApiData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val baseUrl: String = "https://imdb-api.com/en/API/"

    @Singleton
    @Provides
    fun getDatabase(context: Context): TvShowDb {
        return TvShowDb.getDatabase(context)
    }

    @Singleton
    @Provides
    fun getDao(tvShowDb: TvShowDb): TvShowDao {
        return tvShowDb.movieDao()
    }

    @Singleton
    @Provides
    fun getRetrofitServiceInstance(retrofit: Retrofit): ApiData{
        return retrofit.create(ApiData::class.java)
    }

    @Singleton
    @Provides
    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun getContext(): Context {
        return getContext()
    }
}