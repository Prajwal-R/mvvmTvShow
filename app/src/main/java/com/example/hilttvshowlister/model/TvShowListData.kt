package com.example.hilttvshowlister.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tv_show_details")
data class TvShowInformationData(
    @ColumnInfo(name = "Title")
    val title: String,
    @ColumnInfo(name = "Image")
    val image: String,
    @ColumnInfo(name = "Crew")
    val crew: String,
    @ColumnInfo(name = "ImDbRating")
    val imDbRating: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

data class TvShowInformationResponse(
    @SerializedName("title")
    val title: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("crew")
    val crew: String,
    @SerializedName("imDbRating")
    val imDbRating: String
)

data class ImdbResponse(
    @SerializedName("items")
    val items: List<TvShowInformationResponse>,
    @SerializedName("errorMessage")
    val errorMessage: String
)
