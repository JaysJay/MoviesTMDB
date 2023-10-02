package com.sanstv.movies.ui.media.adapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.RatingBar
import com.google.android.material.button.MaterialButton
import com.sanstv.movies.data.model.entities.Movie
import com.sanstv.movies.data.model.entities.Show
import com.sanstv.movies.data.model.tmdb.entities.Cast
import com.sanstv.movies.data.model.tmdb.entities.Media
import com.sanstv.movies.data.model.tmdb.entities.Season
import com.sanstv.movies.data.model.tmdb.entities.Video
import com.sanstv.movies.ui.list.adapter.ListDataModel

interface MediaClickListener {

    fun onClickViewAll(listDataModel: ListDataModel)

    fun onClickMedia(media: Media)
    fun onClickVideo(video: Video)
    fun onClickCast(cast: Cast)

    fun setImageResource(image: Drawable)
    fun setRatingBarView(ratingBar: RatingBar)
    fun setButtonView(button: MaterialButton)

    fun lastSeasonClick(lastSeason: MediaDataModel.LatestSeason)
    fun collectionClick(collectionId: Int)

    fun movieWatchNow(tmdbId: Int)
    fun movieWatchlist(view: MaterialButton, movie: Movie)
    fun movieShare(movie: Movie)

    fun showWatchNow(seasons: List<Season>)
    fun showWatchlist(view: MaterialButton, show: Show)
    fun showShare(show: Show)

    fun openImageInBig(imagePath: String?, imageView: ImageView)

}