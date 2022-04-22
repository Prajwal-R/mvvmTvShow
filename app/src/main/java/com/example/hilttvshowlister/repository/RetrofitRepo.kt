package com.example.hilttvshowlister.repository

import android.content.Context
import com.example.hilttvshowlister.dao.TvShowDao
import com.example.hilttvshowlister.model.TvShowDb
import com.example.hilttvshowlister.model.TvShowInformationData
import com.example.hilttvshowlister.model.TvShowInformationResponse
import com.example.hilttvshowlister.network.RetrofitClass
import io.reactivex.rxjava3.core.Flowable

class RetrofitRepo(val context: Context){

    private val tvShowDao: TvShowDao = TvShowDb.getDatabase(context).movieDao()


    fun getTvShow(itemId: Int): Flowable<TvShowInformationData> {
        return tvShowDao.getData(itemId).toFlowable()
    }

    fun getAllTvShow(): Flowable<List<TvShowInformationData>> {
        return tvShowDao.getAllData().toFlowable()
    }

    fun insertTvShow(tvShowInformationData: MutableList<TvShowInformationData>) {
        tvShowDao.insertData(tvShowInformationData)
    }

    fun getTvShowApiData(): Flowable<List<TvShowInformationResponse>> {
        return RetrofitClass().getData()
    }
}