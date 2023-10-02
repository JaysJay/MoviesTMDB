package com.sanstv.movies.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sanstv.movies.data.model.entities.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMovie(media: Movie): Long

    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<Movie>>

    @Query("SELECT EXISTS(SELECT * FROM movies WHERE id = :id)")
    fun getMovie(id: Int): LiveData<Boolean>

    @Delete
    suspend fun deleteMovie(media: Movie)

}