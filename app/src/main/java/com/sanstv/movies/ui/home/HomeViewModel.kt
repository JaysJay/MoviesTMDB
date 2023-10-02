package com.sanstv.movies.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sanstv.movies.data.model.entities.WatchedMovie
import com.sanstv.movies.data.model.entities.WatchedShow
import com.sanstv.movies.data.model.tmdb.search.SearchResponse
import com.sanstv.movies.data.repository.TmdbRepository
import com.sanstv.movies.data.repository.WatchedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response
import com.sanstv.movies.ui.BaseAndroidViewModel
import com.sanstv.movies.ui.home.adapter.HomeDataModel
import com.sanstv.movies.ui.home.adapter.watched.WatchedDataModel
import com.sanstv.movies.ultility.ext.combineWith
import com.sanstv.movies.ultility.state.Resource
import com.sanstv.movies.ultility.state.ResourceExt.Companion.postError
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    app: Application,
    private val tmdbRepository: TmdbRepository,
    watchedRepository: WatchedRepository
) : BaseAndroidViewModel(app) {

    @Suppress("unused")
    private enum class TrendingWindow {
        DAY, WEEK
    }

    private val now = LocalDate.now()
    private val getTodayDate = now.toString()
    private val getMonthAgoDate = now.minusMonths(1).toString()

    private val _homeMedia = MutableLiveData<Resource<List<HomeDataModel>>>()
    val homeMedia: LiveData<Resource<List<HomeDataModel>>>
        get() = _homeMedia

    init {
        getHomeMedia()
    }

    val movies = watchedRepository.getAllWatchedMovies()
    val shows = watchedRepository.getAllWatchedShows()

    val watchedMedia = movies.combineWith(shows) { movie, show ->
        movie?.let { show?.let { it1 -> handleWatchedMedia(it, it1) } }
    }

    private fun handleWatchedMedia(
        movie: List<WatchedMovie>,
        show: List<WatchedShow>
    ): List<WatchedDataModel> {

        val watchedDataModel = mutableListOf<WatchedDataModel>()

        movie.forEach {
            watchedDataModel.add(WatchedDataModel.Movie(it))
        }

        show.forEach {
            watchedDataModel.add(WatchedDataModel.Show(it))
        }

        watchedDataModel.sortBy {
            when (it) {
                is WatchedDataModel.Show -> it.show.watchProgress()
                is WatchedDataModel.Movie -> it.movie.watchProgress()
            }
        }

        return watchedDataModel.toList()
    }

    private fun getHomeMedia(
    ) = viewModelScope.launch(
        context = Dispatchers.IO + CoroutineExceptionHandler { _, t ->
            t.printStackTrace()
            _homeMedia.postValue(postError(t))
        }
    ) {
        _homeMedia.postValue(Resource.Loading())
        if (hasInternetConnection()) {

            val theatres = async {
                tmdbRepository.getInTheatres(
                    dateStart = getMonthAgoDate,
                    dateEnd = getTodayDate
                )
            }

            val trending = async {
                tmdbRepository.getTrending(
                    timeWindow = TrendingWindow.DAY.toString().lowercase()
                )
            }

            val popular = async {
                tmdbRepository.getPopularOnStreaming()
            }

            val homeResponse = handleHomeResponse(
                theatres.await(),
                trending.await(),
                popular.await()
            )
            _homeMedia.postValue(homeResponse)
        } else {
            _homeMedia.postValue(Resource.Error("No internet connection"))
        }
    }

    private fun handleHomeResponse(
        theatres: Response<SearchResponse>,
        trending: Response<SearchResponse>,
        popular: Response<SearchResponse>
    ): Resource<List<HomeDataModel>> {
        val homeMedia: MutableList<HomeDataModel> = mutableListOf()

        if (theatres.isSuccessful && theatres.body() != null) {
            val theatresList = theatres.body()!!.results
            if (theatresList.isNotEmpty()) {
                homeMedia.add(HomeDataModel.Banner(media = theatresList))
            }
        }

        if (trending.isSuccessful && trending.body() != null) {
            val trendingList = trending.body()!!.results.filter {
                it.backdrop_path != null
            }

            if (trendingList.isNotEmpty()) {
                homeMedia.add(HomeDataModel.Header(heading = "Trending today"))
                homeMedia.add(HomeDataModel.Media(media = trendingList))
            }
        }

        if (popular.isSuccessful && popular.body() != null) {
            val popularList = popular.body()!!.results
            if (popularList.isNotEmpty()) {
                homeMedia.add(HomeDataModel.Header(heading = "Popular on streaming"))
                homeMedia.add(HomeDataModel.Media(media = popularList))
            }
        }

        if (homeMedia.isNotEmpty()) {
            return Resource.Success(homeMedia)
        }

        return Resource.Error(theatres.message())
    }
}