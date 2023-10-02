package com.sanstv.movies.data.remote

import com.sanstv.movies.data.model.MediaType
import com.sanstv.movies.data.model.tmdb.collection.CollectionsResponse
import com.sanstv.movies.data.model.tmdb.entities.Episode
import com.sanstv.movies.data.model.tmdb.keyword.KeywordResponse
import com.sanstv.movies.data.model.tmdb.media.MovieResponse
import com.sanstv.movies.data.model.tmdb.media.TvResponse
import com.sanstv.movies.data.model.tmdb.person.PersonResponse
import com.sanstv.movies.data.model.tmdb.search.SearchResponse
import com.sanstv.movies.data.model.tmdb.season.SeasonResponse
import com.sanstv.movies.ultility.Constants.TMDB_API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    @GET("3/tv/{tv_id}")
    suspend fun getShow(
        @Path("tv_id")
        tvId: Int,
        @Query("api_key")
        apiKey: String = TMDB_API_KEY,
        @Query("language")
        language: String = "en-US",
        @Query("append_to_response")
        appendToResponse: String = "credits,recommendations,videos"
    ): Response<TvResponse>

    @GET("3/tv/{tv_id}/season/{season_number}")
    suspend fun getSeason(
        @Path("tv_id")
        moviesId: Int,
        @Path("season_number")
        seasonNumber: Int,
        @Query("api_key")
        apiKey: String = TMDB_API_KEY,
        @Query("language")
        language: String = "en-US",
    ): Response<SeasonResponse>

    @GET("3/tv/{tv_id}/season/{season_number}/episode/{episode_number}")
    suspend fun getEpisode(
        @Path("tv_id")
        moviesId: Int,
        @Path("season_number")
        seasonNumber: Int,
        @Path("episode_number")
        episodeNumber: Int,
        @Query("api_key")
        apiKey: String = TMDB_API_KEY,
        @Query("language")
        language: String = "en-US",
    ): Response<Episode>

    @GET("3/search/multi")
    suspend fun getSearch(
        @Query("api_key")
        apiKey: String = TMDB_API_KEY,
        @Query("language")
        language: String = "en-US",
        @Query("query")
        query: String,
        @Query("page")
        page: Int = 1,
        @Query("include_adult")
        includeAdult: Boolean = false
    ): Response<SearchResponse>

    @GET("3/movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id")
        movieId: Int,
        @Query("api_key")
        apiKey: String = TMDB_API_KEY,
        @Query("language")
        language: String = "en-US",
        @Query("append_to_response")
        appendToResponse: String = "credits,recommendations,videos"
    ): Response<MovieResponse>

    @GET("3/collection/{collection_id}")
    suspend fun getCollection(
        @Path("collection_id")
        collectionId: Int,
        @Query("api_key")
        apiKey: String = TMDB_API_KEY,
        @Query("language")
        language: String = "en-US"
    ): Response<CollectionsResponse>

    @GET("3/discover/{media_type}")
    suspend fun getBrowse(
        @Path("media_type")
        mediaType: MediaType,
        @Query("api_key")
        apiKey: String = TMDB_API_KEY,
        @Query("language")
        language: String = "en-US",
        @Query("sort_by")
        sortBy: String,
        @Query("page")
        page: Int,
        @Query("with_keywords")
        withKeywords: String?,
        @Query("with_genres")
        withGenres: Int?,
        @Query("include_adult")
        includeAdult: Boolean = false
    ): Response<SearchResponse>


    @GET("3/movie/upcoming")
    suspend fun getUpcoming(
        @Query("api_key")
        apiKey: String = TMDB_API_KEY,
        @Query("language")
        language: String = "en-US",
        @Query("page")
        page: Int,
    ): Response<SearchResponse>

    @GET("3/discover/movie")
    suspend fun getInTheatres(
        @Query("api_key")
        apiKey: String = TMDB_API_KEY,
        @Query("language")
        language: String = "en-US",
        @Query("release_date.gte")
        releaseDateStart: String,
        @Query("release_date.lte")
        releaseDateEnd: String,
        @Query("with_release_type")
        withReleaseType: String = "3|2",
        @Query("region")
        region: String = "US"
    ): Response<SearchResponse>


    @GET("3/trending/all/{time_window}")
    suspend fun getTrending(
        @Path("time_window")
        timeWindow: String,
        @Query("api_key")
        apiKey: String = TMDB_API_KEY,
        @Query("language")
        language: String = "en-US"
    ): Response<SearchResponse>

    @GET("3/tv/on_the_air")
    suspend fun getPopularOnStreaming(
        @Query("api_key")
        apiKey: String = TMDB_API_KEY,
        @Query("language")
        language: String = "en-US",
        @Query("page")
        page: Int = 1,
    ): Response<SearchResponse>

    @GET("3/discover/{media_type}")
    suspend fun getFromCompany(
        @Path("media_type")
        mediaType: MediaType,
        @Query("api_key")
        apiKey: String = TMDB_API_KEY,
        @Query("language")
        language: String = "en-US",
        @Query("page")
        page: Int = 1,
        @Query("include_adult")
        includeAdult: Boolean = false,
        @Query("include_video")
        includeVideo: Boolean = false,
        @Query("with_companies")
        withCompanies: Int
    ): Response<SearchResponse>

    @GET("3/person/{person_id}")
    suspend fun getPerson(
        @Path("person_id")
        personId: Int,
        @Query("api_key")
        apiKey: String = TMDB_API_KEY,
        @Query("language")
        language: String = "en-US",
        @Query("append_to_response")
        appendToResponse: String = "combined_credits"
    ): Response<PersonResponse>


    @GET("3/search/keyword")
    suspend fun searchKeyword(
        @Query("query")
        query: String,
        @Query("api_key")
        apiKey: String = TMDB_API_KEY
    ): Response<KeywordResponse>

}