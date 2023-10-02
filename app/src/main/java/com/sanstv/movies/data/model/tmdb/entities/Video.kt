package com.sanstv.movies.data.model.tmdb.entities

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Video(
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("key")
    val key: String,
    @field:SerializedName("site")
    val site: String,
    @field:SerializedName("type")
    val type: String,
    @field:SerializedName("official")
    val official: Boolean
) {
    val thumbUrl get() = "https://i3.ytimg.com/vi/$key/maxresdefault.jpg"
    val watchUrl get() = "https://www.youtube.com/watch?v=$key"
}