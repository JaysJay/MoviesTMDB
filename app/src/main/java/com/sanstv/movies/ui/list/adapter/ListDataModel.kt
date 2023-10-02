package com.sanstv.movies.ui.list.adapter

import androidx.annotation.Keep
import com.sanstv.movies.data.model.tmdb.entities.Cast
import com.sanstv.movies.data.model.tmdb.entities.Season
import com.sanstv.movies.data.model.tmdb.entities.Video
import com.sanstv.movies.ui.home.adapter.tmdbMedia

sealed class ListDataModel {

    @Keep
    data class Seasons(
        val tmdbId: Int,
        val showName: String,
        val showPoster: String?,
        val seasons: List<Season>
    ) : ListDataModel()

    @Keep
    data class Media(
        val heading: String,
        val media: List<tmdbMedia>
    ) : ListDataModel()

    @Keep
    data class Casts(
        val casts: List<Cast>
    ) : ListDataModel()

    @Keep
    data class Videos(
        val videos: List<Video>
    ) : ListDataModel()

}