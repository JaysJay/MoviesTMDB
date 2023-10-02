package com.sanstv.movies.data.model.tmdb.media

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.sanstv.movies.data.model.tmdb.entities.Cast

@Keep
data class Credits(
    @field:SerializedName("cast")
    val cast: List<Cast>?,
)