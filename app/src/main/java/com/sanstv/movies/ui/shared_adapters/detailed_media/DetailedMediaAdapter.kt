package com.sanstv.movies.ui.shared_adapters.detailed_media

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.sanstv.movies.data.model.tmdb.entities.Media
import com.sanstv.movies.databinding.ItemDetailedMediaBinding
import com.sanstv.movies.ui.shared_adapters.media.MediaItemDiffCallback

class DetailedMediaAdapter(
    val mediaOnClick: (Media) -> Unit
) : ListAdapter<Media, DetailedViewHolder>(MediaItemDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = DetailedViewHolder(
        itemBinding = ItemDetailedMediaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        ),
        detailedMediaAdapter = this
    )

    override fun onBindViewHolder(
        holder: DetailedViewHolder, position: Int
    ) {
        holder.bind(getItem(position))
    }

}