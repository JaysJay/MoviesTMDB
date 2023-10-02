package com.sanstv.movies.ui.shared_adapters.trending

import androidx.recyclerview.widget.RecyclerView

import com.sanstv.movies.R
import com.sanstv.movies.data.model.PosterSize
import com.sanstv.movies.data.model.tmdb.entities.Media
import com.sanstv.movies.databinding.ItemMediaBinding
import com.sanstv.movies.ultility.Constants.TMDB_IMAGE_PREFIX
import com.sanstv.movies.ultility.GlideApp

class TrendingViewHolder(
    private val itemBinding: ItemMediaBinding,
    val trendingAdapter: TrendingAdapter
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(media: Media) {
        val mediaPosterUrl = if (media.poster_path == null) {
            R.drawable.no_poster
        } else {
            "${TMDB_IMAGE_PREFIX}/${PosterSize.w342}${media.poster_path}"
        }
        itemBinding.itemPoster.apply {
            GlideApp.with(this)
                .load(mediaPosterUrl)
                .placeholder(R.drawable.no_poster)
                .into(this)
            setOnClickListener {
                trendingAdapter.mediaOnClick.invoke(media)
            }
        }

    }
}