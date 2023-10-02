package com.sanstv.movies.ui.home.adapter.watched

import androidx.annotation.Keep
import com.sanstv.movies.data.model.entities.WatchedMovie
import com.sanstv.movies.data.model.entities.WatchedShow

sealed class WatchedDataModel {

    @Keep
    data class Show(
        val show: WatchedShow
    ) : WatchedDataModel()

    @Keep
    data class Movie(
        val movie: WatchedMovie
    ) : WatchedDataModel()


}