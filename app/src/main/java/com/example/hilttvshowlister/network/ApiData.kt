package com.example.hilttvshowlister.network

import com.example.hilttvshowlister.model.ImdbResponse
import io.reactivex.rxjava3.core.Flowable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiData {

    @GET("Top250TVs/{apiKey}")
    fun getDataForFragment(@Path("apiKey") apiKey: String): Flowable<Response<ImdbResponse>>
}