package com.sanstv.movies.ui.media

import android.app.Application
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import com.sanstv.movies.data.model.MediaType
import com.sanstv.movies.data.model.entities.Movie
import com.sanstv.movies.data.model.entities.Show
import com.sanstv.movies.data.model.tmdb.media.MovieResponse
import com.sanstv.movies.data.model.tmdb.media.TvResponse
import com.sanstv.movies.data.model.tmdb.search.SearchResponse
import com.sanstv.movies.data.repository.TmdbRepository
import com.sanstv.movies.ui.BaseAndroidViewModel
import com.sanstv.movies.ui.media.adapter.MediaDataModel
import com.sanstv.movies.ultility.state.Resource
import com.sanstv.movies.ultility.state.ResourceExt.Companion.postError
import com.sanstv.movies.ultility.util.Converter
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    app: Application,
    private val tmdbRepository: TmdbRepository
) : BaseAndroidViewModel(app) {

    private val _dominantColor = MutableLiveData<Int>()
    val dominantColor: LiveData<Int>
        get() = _dominantColor

    fun saveShow(show: Show) = viewModelScope.launch {
        tmdbRepository.upsertShow(show)
    }

    fun deleteShow(show: Show) = viewModelScope.launch {
        tmdbRepository.deleteShow(show)
    }

    fun getShow(id: Int) = tmdbRepository.fetchShow(id)

    fun saveMovie(movie: Movie) = viewModelScope.launch {
        tmdbRepository.upsertMovie(movie)
    }

    fun deleteMovie(movie: Movie) = viewModelScope.launch {
        tmdbRepository.deleteMovie(movie)
    }

    fun getMovie(id: Int) = tmdbRepository.fetchMovie(id)

    fun setDominantColor(color: Int) {
        if (_dominantColor.value == color) return
        _dominantColor.value = color
    }

    private val _mediaResponse = MutableLiveData<Resource<List<MediaDataModel>>>()
    val mediaResponse: LiveData<Resource<List<MediaDataModel>>>
        get() = _mediaResponse

    // Handling events or saving data
    var hasLoaded = false

    var tmdbId = 0
        private set

    var showName: String? = null
        private set

    var showPoster: String? = null
        private set

    var mediaType: MediaType = MediaType.tv

    fun getMedia(
        tmdbId: Int,
        mediaType: MediaType
    ) = viewModelScope.launch {
        _mediaResponse.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                when (mediaType) {
                    MediaType.tv -> fetchShow(tmdbId)
                    MediaType.movie -> fetchMovie(tmdbId)
                    else -> {}
                }
            } else {
                _mediaResponse.postValue(Resource.Error("No internet connection"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _mediaResponse.postValue(postError(e))
        }
    }

    private suspend fun fetchMovie(
        tmdbId: Int
    ) = withContext(Dispatchers.IO) {
        val page = 1
        val movie = tmdbRepository.getMovie(tmdbId)

        if (movie.isSuccessful && movie.body() != null) {
            movie.body()!!.production_companies?.let {
                if (it.isNotEmpty()) {
                    val moreFromCompany = tmdbRepository.getMoviesFromCompany(
                        it[0].id, page
                    )
                    val handleMovieResponse = handleMovieResponse(
                        movie, moreFromCompany
                    )
                    _mediaResponse.postValue(handleMovieResponse)
                    return@withContext
                }
            }
        }

        _mediaResponse.postValue(handleMovieResponse(movie, company = null))
    }

    private suspend fun fetchShow(
        tmdbId: Int
    ) = withContext(Dispatchers.IO) {
        val page = 1
        val tv = tmdbRepository.getShow(tmdbId)

        if (tv.isSuccessful && tv.body() != null) {
            tv.body()!!.production_companies?.let { companies ->
                if (companies.isNotEmpty()) {
                    val moreFromCompany = async {
                        tmdbRepository.getShowsFromCompany(
                            companyId = companies[0].id,
                            page = page
                        )
                    }
                    val handleTvResponse = handleTvResponse(
                        tv, moreFromCompany.await()
                    )
                    _mediaResponse.postValue(handleTvResponse)
                    return@withContext
                }
            }
        }

        _mediaResponse.postValue(handleTvResponse(tv, company = null))
    }


    private fun handleTvResponse(
        response: Response<TvResponse>,
        company: Response<SearchResponse>?
    ): Resource<List<MediaDataModel>> {

        if (response.isSuccessful && response.body() != null) {
            val result = response.body()!!
            val mediaDataModel = mutableListOf<MediaDataModel>()

            this@MediaViewModel.showPoster = result.poster_path
            this@MediaViewModel.tmdbId = result.id
            this@MediaViewModel.mediaType = MediaType.tv
            this@MediaViewModel.showName = result.name

            val year = result.firstAirDate?.take(4)?.toInt()

            var runtime = "${
                if (result.episodeRunTime?.isNotEmpty() == true) {
                    result.episodeRunTime[0]
                } else 0
            } min"

            runtime += if (year != null) {
                "  \u2022  $year"
            } else ""

            mediaDataModel.add(
                MediaDataModel.Header(
                    backdropPath = result.backdrop_path,
                    posterPath = result.poster_path,
                    rating = result.vote_average?.div(2) ?: 0.toDouble(),
                    genre = result.genres
                        ?.take(3)
                        ?.joinToString(
                            truncated = "",
                            separator = ", ",
                        ) { it.name }!!,
                    runtime = runtime
                )
            )

            val plot = if (result.overview == null || result.overview == "") {
                "No description"
            } else result.overview

            mediaDataModel.add(
                MediaDataModel.Title(
                    title = result.name!!,
                    plot = plot
                )
            )

            val seasonList = result.seasons?.toList() ?: listOf()
            result.last_episode_to_air?.let { ep ->
                val season = seasonList.firstOrNull {
                    it.season_number == ep.season_number
                }

                season?.let {
                    val seasonName = "Season ${it.season_number}"

                    var premiered = "$seasonName of ${result.name}"
                    var yearSeason = ""

                    val formattedDate = it.air_date?.let { date ->
                        yearSeason += "${date.take(4)} | "
                        Converter.parseDate(date)
                    }

                    yearSeason += "${it.episode_count} episodes"

                    formattedDate?.let {
                        premiered += " premiered on $formattedDate."
                    }
                    val seasonPlot = if (it.overview.isNullOrEmpty()) {
                        premiered
                    } else it.overview

                    mediaDataModel.add(
                        MediaDataModel.LatestSeason(
                            showTmdbId = result.id,
                            showName = result.name,
                            showPoster = result.poster_path,
                            seasonName = seasonName,
                            seasonPosterPath = season.poster_path,
                            seasonNumber = season.season_number,
                            seasonPlot = seasonPlot,
                            seasonYearAndEpisodeCount = yearSeason,
                        )
                    )
                }
            }

            mediaDataModel.add(
                MediaDataModel.ShowButton(
                    show = Show(
                        id = result.id,
                        mediaType = "tv",
                        name = result.name,
                        posterPath = result.poster_path,
                        voteAverage = result.vote_average,
                    ),
                    seasons = seasonList
                )
            )

            result.credits.cast?.let {
                if (it.isNotEmpty()) {
                    mediaDataModel.add(
                        MediaDataModel.Casts(
                            heading = "Casts",
                            casts = it
                        )
                    )
                }
            }

            result.recommendations?.results?.let {
                if (it.isNotEmpty() && it.size >= 3) {
                    mediaDataModel.add(
                        MediaDataModel.Recommendations(
                            heading = "Recommendations",
                            recommendations = it
                        )
                    )
                }
            }

            if (company != null && company.isSuccessful && company.body() != null) {
                val mediaList = company.body()!!.results
                if (mediaList.isNotEmpty() && mediaList.size >= 3) {
                    val studio = result.production_companies!![0].name!!

                    mediaDataModel.add(
                        MediaDataModel.MoreFromCompany(
                            heading = "More from $studio",
                            more = mediaList
                        )
                    )

                }
            }

            result.videos?.results?.let {
                if (it.isNotEmpty()) {
                    mediaDataModel.add(
                        MediaDataModel.Videos(
                            heading = "Related videos",
                            videos = it
                        )
                    )
                }
            }

            return Resource.Success(mediaDataModel.toList())
        }
        return Resource.Error(response.message())
    }

    private fun handleMovieResponse(
        response: Response<MovieResponse>,
        company: Response<SearchResponse>?
    ): Resource<List<MediaDataModel>> {
        if (response.isSuccessful && response.body() != null) {
            val result = response.body()!!
            val mediaDataModel = mutableListOf<MediaDataModel>()

            this@MediaViewModel.showPoster = result.poste_path
            this@MediaViewModel.tmdbId = result.id
            this@MediaViewModel.mediaType = MediaType.movie
            this@MediaViewModel.showName = result.title

            val year: Int? = result.release_date?.let { firstAired ->
                if (firstAired.isNotBlank()) {
                    firstAired.take(4).toInt()
                } else null
            }

            var runtime = ""
            result.runtime?.let {
                runtime += "$it min"
            }
            runtime += if (year != null) {
                "  \u2022  $year"
            } else ""

            mediaDataModel.add(
                MediaDataModel.Header(
                    backdropPath = result.backdrop_path,
                    posterPath = result.poste_path,
                    rating = result.vote_average?.div(2) ?: 0.toDouble(),
                    genre = result.genres?.take(3)?.joinToString(
                        truncated = "",
                        separator = ", ",
                    ) { it.name }!!,
                    runtime = runtime
                )
            )

            val plot = if (result.overview == null || result.overview == "") {
                "No description"
            } else result.overview

            mediaDataModel.add(
                MediaDataModel.Title(
                    title = result.title!!,
                    plot = plot
                )
            )

            result.belongs_to_collection?.let {
                mediaDataModel.add(
                    MediaDataModel.PartOfCollection(
                        bannerPoster = it.backdrop_path,
                        collectionName = it.name!!,
                        collectionId = it.id
                    )
                )
            }

            mediaDataModel.add(
                MediaDataModel.MovieButton(
                    movie = Movie(
                        id = result.id,
                        mediaType = "movie",
                        title = result.title,
                        posterPath = result.poste_path,
                        voteAverage = result.vote_average
                    )
                )
            )

            result.credits.cast?.let {
                if (it.isNotEmpty()) {
                    mediaDataModel.add(
                        MediaDataModel.Casts(
                            heading = "Casts",
                            casts = it
                        )
                    )
                }
            }

            result.recommendations?.results?.let {
                if (it.isNotEmpty() && it.size >= 3) {
                    mediaDataModel.add(
                        MediaDataModel.Recommendations(
                            heading = "Recommendations",
                            recommendations = it
                        )
                    )
                }
            }

            if (company != null && company.isSuccessful && company.body() != null) {
                val mediaList = company.body()!!.results
                if (mediaList.isNotEmpty() && mediaList.size >= 3) {
                    val studio = result.production_companies!![0].name!!

                    mediaDataModel.add(
                        MediaDataModel.MoreFromCompany(
                            heading = "More from $studio",
                            more = mediaList
                        )
                    )
                }
            }

            result.videos?.results?.let {
                if (it.isNotEmpty()) {
                    mediaDataModel.add(
                        MediaDataModel.Videos(
                            heading = "Related videos",
                            videos = it
                        )
                    )
                }
            }

            hasLoaded = true
            return Resource.Success(mediaDataModel.toList())
        }
        return Resource.Error(response.message())
    }


    fun calcDominantColor(drawable: Drawable, onFinish: (Int) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(
            Bitmap.Config.ARGB_8888, true
        )
        Palette.from(bmp).generate { p ->
            try {
                onFinish(p!!.vibrantSwatch?.rgb ?: p.dominantSwatch?.rgb ?: 6770852)
            } catch (npe: NullPointerException) {
                onFinish(6770852)
            }
        }
    }
}