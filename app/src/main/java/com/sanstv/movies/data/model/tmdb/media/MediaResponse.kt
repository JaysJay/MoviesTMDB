package com.sanstv.movies.data.model.tmdb.media

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.sanstv.movies.data.model.tmdb.entities.Cast
import com.sanstv.movies.data.model.tmdb.entities.Episode
import com.sanstv.movies.data.model.tmdb.entities.Genre
import com.sanstv.movies.data.model.tmdb.entities.Media
import com.sanstv.movies.data.model.tmdb.entities.Season
import com.sanstv.movies.data.model.tmdb.entities.Video

@Keep
data class MediaResponse(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("imdb_id")
    val imdb_id: String?,
    @field:SerializedName("backdrop_path")
    val backdrop_path: String?,
    @field:SerializedName("name")
    val name: String?,
    @field:SerializedName("overview")
    val overview: String?,
    @field:SerializedName("poster_path")
    val poster_path: String?,
    @field:SerializedName("related_media")
    val related_media: List<Media>,
    @field:SerializedName("seasons")
    val seasons: List<Season>,
    @field:SerializedName("cast")
    val cast: List<Cast>,
    @field:SerializedName("recommendations")
    val recommendations: List<Media>,
    @field:SerializedName("videos")
    val videos: List<Video>,
    @field:SerializedName("vote_average")
    val vote_average: Double?,
    @field:SerializedName("genres")
    val genres: List<Genre>?,
    @field:SerializedName("runtime")
    val runtime: Int?,
    @field:SerializedName("year")
    val year: Int?,
    @field:SerializedName("belongs_to_collection")
    val belongs_to_collection: Media?,
    @field:SerializedName("last_episode_to_air")
    val last_episode_to_air: Episode?
)