package com.sanstv.movies.data.model.tmdb.entities

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Episode(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String?,
    @field:SerializedName("overview")
    val overview: String?,
    @field:SerializedName("episode_number")
    val episode_number: Int,
    @field:SerializedName("season_number")
    val season_number: Int,
    @field:SerializedName("still_path")
    val still_path: String?,
    @field:SerializedName("guest_stars")
    val guest_stars: List<Cast>?
)