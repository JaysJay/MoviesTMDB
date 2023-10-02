package com.sanstv.movies.ui.search

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import com.sanstv.movies.data.model.tmdb.search.SearchResponse
import com.sanstv.movies.data.repository.TmdbRepository
import com.sanstv.movies.ui.BaseAndroidViewModel
import com.sanstv.movies.ultility.state.Resource
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    app: Application,
    private val tmdbRepository: TmdbRepository
) : BaseAndroidViewModel(app) {

    private val _searchResponse = MutableLiveData<Resource<SearchResponse>>()
    val searchResponse: LiveData<Resource<SearchResponse>>
        get() = _searchResponse

    private var newSearchQuery: String? = null
    private var oldSearchQuery: String? = null
    internal var page = 1
    private var responseSearch: SearchResponse? = null

    fun getSearch(query: String) = viewModelScope.launch {
        if (query != oldSearchQuery) {
            page = 1
        }
        newSearchQuery = query
        _searchResponse.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = tmdbRepository.getSearch(query, page)
                _searchResponse.postValue(handleSearchResponse(response))
            } else {
                _searchResponse.postValue(Resource.Error("No internet connection"))
            }
        } catch (e: Exception) {
            e.printStackTrace()

            val errorMsg = if (e is IOException) {
                "Network Failure"
            } else e.message ?: "Something went wrong"
            _searchResponse.postValue(Resource.Error(errorMsg))
        }
    }

    private fun handleSearchResponse(
        response: Response<SearchResponse>
    ): Resource<SearchResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if (responseSearch == null || newSearchQuery != oldSearchQuery) {
                    page = 2
                    oldSearchQuery = newSearchQuery
                    responseSearch = resultResponse
                } else {
                    page++
                    val oldResults = responseSearch?.results
                    val newResults = resultResponse.results
                    oldResults?.addAll(newResults)
                }
                return Resource.Success(responseSearch ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}