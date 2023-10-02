package com.sanstv.movies.data.model.tmdb.keyword

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class KeywordResponse(
    @field:SerializedName("page")
    val page: Int,
    @field:SerializedName("results")
    val results: List<TmdbKeyword>,
    @field:SerializedName("total_pages")
    val total_pages: Int,
    @field:SerializedName("total_results")
    val total_results: Int
)