package com.sanstv.movies.data.model.tmdb.collection

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.sanstv.movies.data.model.tmdb.entities.Media

@Keep
data class CollectionsResponse(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("overview")
    val overview: String?,
    @field:SerializedName("parts")
    val parts: List<Media>,
    @field:SerializedName("poster_path")
    val poster_path: String?,
    @field:SerializedName("backdrop_path")
    val backdrop_path: String?
)