package com.sanstv.movies.data.model.tmdb.entities

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class Cast(
    @field:SerializedName("character")
    val character: String,
    @field:SerializedName("credit_id")
    val credit_id: String,
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("profile_path")
    val profile_path: String?
) : Serializable