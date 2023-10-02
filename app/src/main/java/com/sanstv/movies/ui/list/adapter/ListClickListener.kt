package com.sanstv.movies.ui.list.adapter

import com.sanstv.movies.data.model.tmdb.entities.Cast
import com.sanstv.movies.data.model.tmdb.entities.Media
import com.sanstv.movies.data.model.tmdb.entities.Season
import com.sanstv.movies.data.model.tmdb.entities.Video

interface ListClickListener {

    fun onClickSeason(season: Season)
    fun onClickMedia(media: Media)
    fun onClickCast(cast: Cast)
    fun onClickVideo(video: Video)

}