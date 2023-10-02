package com.sanstv.movies.data.model.tmdb.entities

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Network(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("logo_path")
    val logo_path: String?,
    @field:SerializedName("name")
    val name: String?,
    @field:SerializedName("origin_country")
    val origin_country: String?
)