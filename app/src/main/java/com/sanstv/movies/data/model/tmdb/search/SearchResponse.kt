package com.sanstv.movies.data.model.tmdb.search

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.sanstv.movies.data.model.tmdb.entities.Media

@Keep
data class SearchResponse(
    @field:SerializedName("page")
    val page: Int,
    @field:SerializedName("results")
    val results: MutableList<Media>,
    @field:SerializedName("total_pages")
    val total_pages: Int,
    @field:SerializedName("total_results")
    val total_results: Int
)