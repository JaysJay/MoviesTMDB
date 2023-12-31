package com.sanstv.movies.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sanstv.movies.data.model.entities.Show

@Dao
interface ShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertShow(media: Show): Long

    @Query("SELECT * FROM shows")
    fun getAllShows(): LiveData<List<Show>>

    @Query("SELECT EXISTS(SELECT * FROM Shows WHERE id = :id)")
    fun getShow(id: Int): LiveData<Boolean>

    @Delete
    suspend fun deleteShow(media: Show)

}