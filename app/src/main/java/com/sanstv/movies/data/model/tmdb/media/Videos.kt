package com.sanstv.movies.data.model.tmdb.media

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.sanstv.movies.data.model.tmdb.entities.Video

@Keep
data class Videos(
    @field:SerializedName("page")
    val page: Int?,
    @field:SerializedName("results")
    val results: List<Video>?,
    @field:SerializedName("total_pages")
    val total_pages: Int?,
    @field:SerializedName("total_results")
    val total_results: Int?
)