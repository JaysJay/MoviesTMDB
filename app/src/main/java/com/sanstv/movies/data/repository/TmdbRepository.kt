package com.sanstv.movies.data.repository

import android.util.Log
import com.sanstv.movies.data.model.MediaType
import com.sanstv.movies.data.model.Order
import com.sanstv.movies.data.model.SortBy
import com.sanstv.movies.data.model.entities.Movie
import com.sanstv.movies.data.model.entities.Show
import com.sanstv.movies.data.model.tmdb.keyword.TmdbKeyword
import com.sanstv.movies.data.model.tmdb.search.SearchResponse
import com.sanstv.movies.data.remote.TmdbApi
import com.sanstv.movies.data.room.MovieDao
import com.sanstv.movies.data.room.ShowDao
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TmdbRepository @Inject constructor(
    private val tmdbApi: TmdbApi,
    private val movieDao: MovieDao,
    private val showDao: ShowDao
) {

    companion object {
        private const val TAG = "TmdbRepository"
    }

    suspend fun upsertMovie(
        movie: Movie
    ) = movieDao.upsertMovie(movie)

    fun fetchMovie(
        id: Int
    ) = movieDao.getMovie(id)

    suspend fun deleteMovie(
        movie: Movie
    ) = movieDao.deleteMovie(movie)

    fun getSavedMovies() = movieDao.getAllMovies()

    suspend fun upsertShow(
        show: Show
    ) = showDao.upsertShow(show)

    fun fetchShow(
        id: Int
    ) = showDao.getShow(id)

    suspend fun deleteShow(
        show: Show
    ) = showDao.deleteShow(show)

    fun getSavedShows() = showDao.getAllShows()

    suspend fun getShow(
        tvId: Int
    ) = tmdbApi.getShow(tvId)

    suspend fun getMovie(
        movieId: Int
    ) = tmdbApi.getMovie(movieId)

    suspend fun getSeason(
        tvId: Int,
        seasonNumber: Int
    ) = tmdbApi.getSeason(tvId, seasonNumber)

    suspend fun getEpisode(
        tvId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ) = tmdbApi.getEpisode(tvId, seasonNumber, episodeNumber)

    suspend fun getSearch(
        query: String,
        page: Int
    ) = tmdbApi.getSearch(query = query, page = page)

    suspend fun getCollection(
        collectionId: Int
    ) = tmdbApi.getCollection(collectionId = collectionId)

    suspend fun getTrending(
        timeWindow: String
    ) = tmdbApi.getTrending(timeWindow)

    suspend fun getUpcoming(page: Int) = tmdbApi.getUpcoming(page = page)

    suspend fun getBrowse(
        mediaType: MediaType,
        sortBy: SortBy,
        order: Order,
        page: Int,
        withKeyword: List<TmdbKeyword>?,
        withGenres: Int?,
    ): Response<SearchResponse> {
        val keywords = try {
            withKeyword
                ?.map { keyword -> keyword.id }
                ?.joinToString(separator = ",")
        } catch (e: Exception) {
            Log.d(TAG, e.message ?: "Unable to parse keywords")
            null
        }
        return tmdbApi.getBrowse(
            mediaType = mediaType,
            sortBy = "${sortBy.name}.${order.name}",
            page = page,
            withKeywords = keywords,
            withGenres = withGenres,
        )
    }

    suspend fun getInTheatres(
        dateStart: String, dateEnd: String
    ) = tmdbApi.getInTheatres(
        releaseDateStart = dateStart,
        releaseDateEnd = dateEnd
    )

    suspend fun getPopularOnStreaming(
    ) = tmdbApi.getPopularOnStreaming()

    suspend fun getShowsFromCompany(
        companyId: Int,
        page: Int
    ) = tmdbApi.getFromCompany(
        mediaType = MediaType.tv,
        withCompanies = companyId,
        page = page
    )

    suspend fun getMoviesFromCompany(
        companyId: Int,
        page: Int
    ) = tmdbApi.getFromCompany(
        mediaType = MediaType.movie,
        withCompanies = companyId,
        page = page
    )

    suspend fun getPerson(
        personId: Int
    ) = tmdbApi.getPerson(
        personId = personId
    )

    suspend fun searchKeyword(
        query: String
    ) = tmdbApi.searchKeyword(
        query = query
    )

}