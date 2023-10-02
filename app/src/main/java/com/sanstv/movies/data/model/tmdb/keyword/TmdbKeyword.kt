package com.sanstv.movies.data.model.tmdb.keyword

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TmdbKeyword(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String
)