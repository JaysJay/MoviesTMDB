package com.sanstv.movies.ui.shared_viewmodels

import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sanstv.movies.data.model.MediaType
import com.sanstv.movies.data.model.Order
import com.sanstv.movies.data.model.SortBy
import com.sanstv.movies.data.model.tmdb.keyword.TmdbKeyword

@Keep
data class FilterArgs(
    val mediaType: MediaType,
    val sortBy: SortBy,
    val order: Order,
    val page: Int,
    val withKeyword: List<TmdbKeyword>?,
    val withGenres: Int?
)

class FiltersViewModel : ViewModel() {

    private val _filterArgs = MutableLiveData<FilterArgs>()
    val filterArgs: LiveData<FilterArgs> get() = _filterArgs

    init {
        setFilter(
            mediaType = MediaType.movie,
            sortBy = SortBy.popularity,
            order = Order.desc,
            page = 1,
            withKeyword = null,
            withGenres = null
        )
    }

    fun setFilter(
        mediaType: MediaType,
        sortBy: SortBy,
        order: Order,
        page: Int,
        withKeyword: List<TmdbKeyword>?,
        withGenres: Int?
    ) {
        val update = FilterArgs(mediaType, sortBy, order, page, withKeyword, withGenres)
        if (_filterArgs.value == update) return
        _filterArgs.value = update
    }

    fun getFilter() = _filterArgs.value
}
