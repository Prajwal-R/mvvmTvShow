package com.example.hilttvshowlister.model

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hilttvshowlister.dao.TvShowDao

@Database(entities = [TvShowInformationData::class], version = 1, exportSchema = false)
abstract class TvShowDb : RoomDatabase() {

    abstract fun movieDao(): TvShowDao

    companion object {
        @Volatile
        private var instance: TvShowDb? = null

        @JvmStatic
        fun getDatabase(context: Context): TvShowDb {
            Log.d("TAG", "getData: prajwal get database in Tvshowdb")
            var INSTANCE = instance
            if (INSTANCE != null) {
                return INSTANCE
            }
            synchronized(this) {
                val databaseInstance = Room.databaseBuilder(
                    context.applicationContext,
                    TvShowDb::class.java,
                    "movie_details"
                ).build()
                //instance = databaseInstance
                return databaseInstance
            }
        }
    }
}