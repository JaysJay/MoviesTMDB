package com.sanstv.movies.ui.episodes.adapter

import androidx.annotation.Keep

sealed class EpisodesDataModel {

    @Keep
    data class Header(
        val seasonNumber: String,
        val seasonName: String?,
        val seasonPosterPath: String?,
        val seasonOverview: String
    ) : EpisodesDataModel()

    @Keep
    data class Episode(
        val id: Int,
        val name: String,
        val overview: String?,
        val episodeNumber: Int,
        val seasonNumber: Int,
        val stillPath: String?
    ) : EpisodesDataModel()

}