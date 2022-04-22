package com.example.hilttvshowlister.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hilttvshowlister.model.TvShowInformationData
import io.reactivex.rxjava3.core.Single

@Dao
interface TvShowDao {

    @Insert
    fun insertData(tvShowList: List<TvShowInformationData>)

    @Query("SELECT * FROM tv_show_details")
    fun getAllData(): Single<List<TvShowInformationData>>

    @Query("SELECT * FROM tv_show_details WHERE id = :itemId")
    fun getData(itemId: Int): Single<TvShowInformationData>


}