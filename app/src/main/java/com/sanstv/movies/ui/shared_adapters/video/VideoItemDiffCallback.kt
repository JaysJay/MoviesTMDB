package com.sanstv.movies.ui.shared_adapters.video

import androidx.recyclerview.widget.DiffUtil
import com.sanstv.movies.data.model.tmdb.entities.Video

open class VideoItemDiffCallback : DiffUtil.ItemCallback<Video>() {

    override fun areItemsTheSame(
        oldItem: Video, newItem: Video
    ) = oldItem.key == newItem.key

    override fun areContentsTheSame(
        oldItem: Video, newItem: Video
    ) = oldItem == newItem

}