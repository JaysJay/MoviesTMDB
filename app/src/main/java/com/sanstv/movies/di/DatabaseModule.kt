package com.sanstv.movies.di

import android.content.Context
import androidx.room.Room
import com.sanstv.movies.data.room.MIGRATION_1_2
import com.sanstv.movies.data.room.MovieDao
import com.sanstv.movies.data.room.ShowDao
import com.sanstv.movies.data.room.WatchedMovieDao
import com.sanstv.movies.data.room.WatchedShowDao
import com.sanstv.movies.data.room.WatchlistDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "sanstv_db.db"

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ) = Room.databaseBuilder(
        appContext,
        WatchlistDatabase::class.java,
        DATABASE_NAME
    ).addMigrations(
        MIGRATION_1_2
    ).build()

    @Singleton
    @Provides
    fun provideMovieDao(
        db: WatchlistDatabase
    ): MovieDao {
        return db.getMovieDao()
    }

    @Singleton
    @Provides
    fun provideShowDao(
        db: WatchlistDatabase
    ): ShowDao {
        return db.getShowDao()
    }

    @Singleton
    @Provides
    fun provideWatchedMovieDao(
        db: WatchlistDatabase
    ): WatchedMovieDao {
        return db.getWatchedMovieDao()
    }

    @Singleton
    @Provides
    fun provideWatchedShowDao(
        db: WatchlistDatabase
    ): WatchedShowDao {
        return db.getWatchedShowDao()
    }

}