package com.sanstv.movies.data.model.tmdb.entities

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.sanstv.movies.data.model.MediaType
import com.sanstv.movies.ultility.util.Converter
import java.io.Serializable

@Keep
data class Media(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("media_type")
    val media_type: MediaType?,
    @field:SerializedName("name")
    val name: String?,
    @field:SerializedName("poster_path")
    val poster_path: String?,
    @field:SerializedName("title")
    val title: String?,
    @field:SerializedName("vote_average")
    val vote_average: Double?,
    @field:SerializedName("backdrop_path")
    val backdrop_path: String?,
    @field:SerializedName("overview")
    val overview: String?,
    @field:SerializedName("release_date")
    val release_date: String?,
    @field:SerializedName("first_air_date")
    val first_air_date: String?
) : Serializable {

    fun releasedDate(): String? {
        val date = release_date ?: first_air_date
        return if (date != null && date != "") {
            Converter.parseDate(date, dstPattern = "MMM dd, yyyy")
        } else null
    }

}