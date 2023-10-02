package com.sanstv.movies.ui.shared_adapters.casts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.sanstv.movies.data.model.tmdb.entities.Cast
import com.sanstv.movies.databinding.ItemCastBinding

class CastAdapter(
    val castOnClick: (Cast) -> Unit
) : ListAdapter<Cast, CastViewHolder>(CastItemDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = CastViewHolder(
        itemBinding = ItemCastBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        ),
        castAdapter = this
    )

    override fun onBindViewHolder(
        holder: CastViewHolder, position: Int
    ) {
        holder.bind(getItem(position))
    }

}