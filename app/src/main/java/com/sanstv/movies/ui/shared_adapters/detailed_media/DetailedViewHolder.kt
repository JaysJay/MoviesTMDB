package com.sanstv.movies.ui.shared_adapters.detailed_media

import androidx.recyclerview.widget.RecyclerView

import com.sanstv.movies.R
import com.sanstv.movies.data.model.PosterSize
import com.sanstv.movies.data.model.tmdb.entities.Media
import com.sanstv.movies.databinding.ItemDetailedMediaBinding
import com.sanstv.movies.ultility.Constants.TMDB_IMAGE_PREFIX
import com.sanstv.movies.ultility.GlideApp

class DetailedViewHolder(
    private val itemBinding: ItemDetailedMediaBinding,
    val detailedMediaAdapter: DetailedMediaAdapter
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(media: Media) {
        val posterUrl = if (media.poster_path == null) {
            R.drawable.no_poster
        } else {
            "${TMDB_IMAGE_PREFIX}/${PosterSize.w342}${media.poster_path}"
        }

        itemBinding.apply {

            root.setOnClickListener {
                detailedMediaAdapter.mediaOnClick.invoke(media)
            }

            ivPoster.apply {
                GlideApp.with(this)
                    .load(posterUrl)
                    .placeholder(R.drawable.no_poster)
                    .into(this)
            }

            var releasing = "Release date not available"
            media.releasedDate()?.let { releasing = it }
            tvYear.text = releasing

            tvTitle.text = media.name ?: media.title
            tvPlot.text = media.overview
        }

    }
}