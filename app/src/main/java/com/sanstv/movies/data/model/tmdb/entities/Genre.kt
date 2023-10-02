package com.sanstv.movies.data.model.tmdb.entities

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Genre(
    @field:SerializedName("id")
    val id: Int?,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("mediaType")
    val mediaType: String?,
    @field:SerializedName("keyword")
    val keyword: Int? = null
)