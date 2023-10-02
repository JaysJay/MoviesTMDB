package com.sanstv.movies.data.model.entities

import androidx.annotation.Keep
import androidx.room.Entity
import com.sanstv.movies.data.model.MediaType
import com.sanstv.movies.data.model.tmdb.entities.Media

@Entity(
    tableName = "movies",
    primaryKeys = ["id"]
)
@Keep
data class Movie(
    val id: Int,
    val title: String,
    val mediaType: String?,
    val posterPath: String?,
    val voteAverage: Double?
) {

    fun toMedia() = Media(
        id = id,
        media_type = MediaType.movie,
        name = null,
        poster_path = posterPath,
        title = title,
        vote_average = voteAverage,
        backdrop_path = null,
        overview = null,
        release_date = null,
        first_air_date = null
    )

}