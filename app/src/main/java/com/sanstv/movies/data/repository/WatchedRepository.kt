package com.sanstv.movies.data.repository

import com.sanstv.movies.data.model.entities.WatchedMovie
import com.sanstv.movies.data.model.entities.WatchedShow
import com.sanstv.movies.data.room.WatchedMovieDao
import com.sanstv.movies.data.room.WatchedShowDao
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WatchedRepository @Inject constructor(
    private val watchedMovieDao: WatchedMovieDao,
    private val watchedShowDao: WatchedShowDao
) {
    suspend fun upsertWatchedMovie(
        watchedMovie: WatchedMovie
    ) = watchedMovieDao.upsertWatchedMovie(watchedMovie)

    suspend fun deleteWatchedMovie(
        watchedMovie: WatchedMovie
    ) = watchedMovieDao.deleteWatchedMovie(watchedMovie)

    suspend fun getWatchedMovie(
        tmdbId: Int
    ) = watchedMovieDao.getWatchedMovie(tmdbId)

    fun getAllWatchedMovies() = watchedMovieDao.getAllWatchedMovies()

    suspend fun upsertWatchedShow(
        watchedShow: WatchedShow
    ) = watchedShowDao.upsertWatchedShow(watchedShow)

    suspend fun deleteWatchedShow(
        watchedShow: WatchedShow
    ) = watchedShowDao.deleteWatchedShow(watchedShow)

    suspend fun getWatchedShow(
        tmdbId: Int
    ) = watchedShowDao.getWatchedShow(tmdbId)

    fun getAllWatchedShows() = watchedShowDao.getAllWatchedShows()

}
