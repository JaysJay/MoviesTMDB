package com.sanstv.movies.data.model.tmdb.person

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.sanstv.movies.data.model.tmdb.entities.Media

@Keep
data class CombinedCredits(
    @field:SerializedName("cast")
    val cast: MutableList<Media>?
)