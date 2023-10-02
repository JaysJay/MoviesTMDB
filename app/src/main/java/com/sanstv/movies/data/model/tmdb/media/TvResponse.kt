package com.sanstv.movies.data.model.tmdb.media

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.sanstv.movies.data.model.tmdb.entities.Episode
import com.sanstv.movies.data.model.tmdb.entities.Genre
import com.sanstv.movies.data.model.tmdb.entities.Network
import com.sanstv.movies.data.model.tmdb.entities.Season

@Keep
data class TvResponse(
    @field:SerializedName("episode_run_time")
    val episodeRunTime: List<Int>?,
    @field:SerializedName("first_air_date")
    val firstAirDate: String?,
    @field:SerializedName("genres")
    val genres: List<Genre>?,
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("imdb_id")
    val imdb_id: String?,
    @field:SerializedName("name")
    val name: String?,
    @field:SerializedName("networks")
    val networks: List<Network>?,
    @field:SerializedName("production_companies")
    val production_companies: List<Network>?,
    @field:SerializedName("overview")
    val overview: String?,
    @field:SerializedName("poster_path")
    val poster_path: String?,
    @field:SerializedName("backdrop_path")
    val backdrop_path: String?,
    @field:SerializedName("seasons")
    val seasons: MutableList<Season>?,
    @field:SerializedName("credits")
    val credits: Credits,
    @field:SerializedName("recommendations")
    val recommendations: Recommendations?,
    @field:SerializedName("videos")
    val videos: Videos?,
    @field:SerializedName("vote_average")
    val vote_average: Double?,
    @field:SerializedName("last_episode_to_air")
    val last_episode_to_air: Episode?
)