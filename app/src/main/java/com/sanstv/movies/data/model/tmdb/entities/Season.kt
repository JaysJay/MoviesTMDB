package com.sanstv.movies.data.model.tmdb.entities

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Season(
    @field:SerializedName("episode_count")
    val episode_count: Int,
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("poster_path")
    val poster_path: String?,
    @field:SerializedName("season_number")
    val season_number: Int,
    @field:SerializedName("overview")
    val overview: String?,
    @field:SerializedName("air_date")
    val air_date: String?
)