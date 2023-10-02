package com.sanstv.movies.ui.shared_adapters.banner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.sanstv.movies.data.model.tmdb.entities.Media
import com.sanstv.movies.databinding.ItemWideBannerBinding

class BannerAdapter(
    val bannerOnClick: (Media) -> Unit
) : ListAdapter<Media, BannerViewHolder>(BannerItemDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = BannerViewHolder(
        itemBinding = ItemWideBannerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        ),
        bannerAdapter = this
    )

    override fun onBindViewHolder(
        holder: BannerViewHolder, position: Int
    ) {
        holder.bind(getItem(position))
    }

}