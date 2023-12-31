package com.sanstv.movies.ui.shared_adapters.video

import androidx.recyclerview.widget.RecyclerView
import com.sanstv.movies.R
import com.sanstv.movies.data.model.tmdb.entities.Video
import com.sanstv.movies.databinding.ItemVideoBinding
import com.sanstv.movies.ultility.GlideApp

class VideoViewHolder(
    private val itemBinding: ItemVideoBinding,
    val videoAdapter: VideoAdapter
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(video: Video) {

        itemBinding.apply {
            ivBackdrop.apply {
                GlideApp.with(this)
                    .load(video.thumbUrl)
                    .placeholder(R.drawable.no_thumb)
                    .into(ivBackdrop)
            }

            tvSource.text = video.site
            tvVideoName.text = video.name

            root.setOnClickListener {
                videoAdapter.videoOnClick.invoke(video)
            }

        }
    }
}