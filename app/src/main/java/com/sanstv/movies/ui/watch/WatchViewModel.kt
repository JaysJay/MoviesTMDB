package com.sanstv.movies.ui.watch

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import com.sanstv.movies.data.model.tmdb.entities.Episode
import com.sanstv.movies.data.repository.TmdbRepository
import com.sanstv.movies.ui.BaseAndroidViewModel
import com.sanstv.movies.ultility.state.Resource
import com.sanstv.movies.ultility.state.ResourceExt.Companion.postError
import javax.inject.Inject

@HiltViewModel
class WatchViewModel @Inject constructor(
    app: Application,
    private val tmdbRepository: TmdbRepository
) : BaseAndroidViewModel(app) {

    private val _episode = MutableLiveData<Resource<Episode>>()
    val episode: LiveData<Resource<Episode>>
        get() = _episode

    fun getEpisode(
        tvId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ) = viewModelScope.launch(Dispatchers.IO) {
        _episode.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = tmdbRepository.getEpisode(tvId, seasonNumber, episodeNumber)
                _episode.postValue(handleEpisodeResponse(response))
            } else {
                _episode.postValue(Resource.Error("No internet connection"))
            }
        } catch (e: Exception) {
            _episode.postValue(postError(e))
        }
    }

    private fun handleEpisodeResponse(
        response: Response<Episode>
    ): Resource<Episode> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}