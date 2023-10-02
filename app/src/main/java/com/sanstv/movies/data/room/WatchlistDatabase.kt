package com.sanstv.movies.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sanstv.movies.data.model.entities.Movie
import com.sanstv.movies.data.model.entities.Show
import com.sanstv.movies.data.model.entities.WatchedMovie
import com.sanstv.movies.data.model.entities.WatchedShow


@Database(
    entities = [
        Movie::class,
        Show::class,
        WatchedMovie::class,
        WatchedShow::class
    ],
    version = 1,
    exportSchema = false
)

abstract class WatchlistDatabase : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao
    abstract fun getShowDao(): ShowDao
    abstract fun getWatchedMovieDao(): WatchedMovieDao
    abstract fun getWatchedShowDao(): WatchedShowDao

}