package com.sanstv.movies.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sanstv.movies.data.model.tmdb.entities.Cast
import com.sanstv.movies.data.model.tmdb.entities.Media
import com.sanstv.movies.data.model.tmdb.entities.Season
import com.sanstv.movies.data.model.tmdb.entities.Video
import com.sanstv.movies.ui.list.adapter.ListDataModel
import com.sanstv.movies.ultility.state.Event

class ListViewModel : ViewModel() {

    private val _listArgs = MutableLiveData<Event<ListDataModel>>()
    val listArgs: LiveData<Event<ListDataModel>> get() = _listArgs

    fun setSeasonsList(
        tmdbId: Int,
        showName: String,
        showPoster: String?,
        seasons: List<Season>
    ) {
        setList(ListDataModel.Seasons(tmdbId, showName, showPoster, seasons))
    }

    fun setCasts(casts: List<Cast>) {
        setList(ListDataModel.Casts(casts = casts))
    }

    fun setMedia(heading: String, media: List<Media>) {
        setList(ListDataModel.Media(heading, media))
    }

    fun setVideo(video: List<Video>) {
        setList(ListDataModel.Videos(video))
    }

    private fun <T : ListDataModel> setList(list: T) {
        _listArgs.value = Event(list)
    }

}