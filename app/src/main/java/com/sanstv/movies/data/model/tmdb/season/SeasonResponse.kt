package com.sanstv.movies.data.model.tmdb.season

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.sanstv.movies.data.model.tmdb.entities.Episode

@Keep
data class SeasonResponse(
    @field:SerializedName("episodes")
    val episodes: List<Episode>?,
    @field:SerializedName("id")
    val id: Int?,
    @field:SerializedName("name")
    val name: String?,
    @field:SerializedName("overview")
    val overview: String?,
    @field:SerializedName("poster_path")
    val poster_path: String?,
    @field:SerializedName("season_number")
    val season_number: Int?
)