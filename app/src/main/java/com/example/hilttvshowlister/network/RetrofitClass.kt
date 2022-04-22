package com.example.hilttvshowlister.network

import com.example.hilttvshowlister.model.TvShowInformationResponse
import io.reactivex.rxjava3.core.Flowable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClass {
    val URL = "https://imdb-api.com/en/API/"
    val apiKey = "k_am6dwjyf"
    fun retrofitInstance() = Retrofit.Builder()
        .baseUrl(URL)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getData(): Flowable<List<TvShowInformationResponse>> {
        return retrofitInstance()
            .create(ApiData::class.java)
            .getDataForFragment(apiKey)
            .map {
                return@map it.body()?.items
            }
    }
}
