package com.sanstv.movies.data.model.entities

import androidx.annotation.Keep
import androidx.room.Entity
import com.sanstv.movies.data.model.MediaType
import com.sanstv.movies.data.model.tmdb.entities.Media

@Entity(
    tableName = "shows",
    primaryKeys = ["id"]
)
@Keep
data class Show(
    val id: Int,
    val name: String,
    val mediaType: String?,
    val posterPath: String?,
    val voteAverage: Double?
) {

    fun toMedia() = Media(
        id = id,
        media_type = MediaType.tv,
        name = name,
        poster_path = posterPath,
        title = null,
        vote_average = voteAverage,
        backdrop_path = null,
        overview = null,
        release_date = null,
        first_air_date = null
    )

}