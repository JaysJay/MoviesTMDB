package com.sanstv.movies.ui.shared_adapters.season

import androidx.recyclerview.widget.DiffUtil
import com.sanstv.movies.data.model.tmdb.entities.Season

class SeasonItemDiffCallback : DiffUtil.ItemCallback<Season>() {

    override fun areItemsTheSame(
        oldItem: Season, newItem: Season
    ) = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Season, newItem: Season
    ) = oldItem == newItem

}