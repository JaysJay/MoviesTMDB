package com.sanstv.movies.data.model.tmdb.media

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.sanstv.movies.data.model.tmdb.entities.Genre
import com.sanstv.movies.data.model.tmdb.entities.Media
import com.sanstv.movies.data.model.tmdb.entities.Network

@Keep
data class MovieResponse(
    @field:SerializedName("title")
    val title: String?,
    @field:SerializedName("runtime")
    val runtime: Int?,
    @field:SerializedName("release_date")
    val release_date: String?,
    @field:SerializedName("genres")
    val genres: List<Genre>?,
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("imdb_id")
    val imdb_id: String?,
    @field:SerializedName("production_companies")
    val production_companies: List<Network>?,
    @field:SerializedName("belongs_to_collection")
    val belongs_to_collection: Media?,
    @field:SerializedName("overview")
    val overview: String?,
    @field:SerializedName("poste_path")
    val poste_path: String?,
    @field:SerializedName("backdrop_path")
    val backdrop_path: String?,
    @field:SerializedName("credits")
    val credits: Credits,
    @field:SerializedName("recommendations")
    val recommendations: Recommendations?,
    @field:SerializedName("videos")
    val videos: Videos?,
    @field:SerializedName("vote_average")
    val vote_average: Double?
)