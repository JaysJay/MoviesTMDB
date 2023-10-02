package com.sanstv.movies.ui.home.adapter

import com.sanstv.movies.data.model.tmdb.entities.Media
import com.sanstv.movies.ui.home.adapter.watched.WatchedDataModel

interface HomeClickListener {

    fun onClickMedia(media: Media)
    fun onClickWatched(watched: WatchedDataModel)

}