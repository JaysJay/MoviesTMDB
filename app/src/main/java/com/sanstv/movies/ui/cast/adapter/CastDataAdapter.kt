package com.sanstv.movies.ui.cast.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import com.sanstv.movies.R
import com.sanstv.movies.data.model.tmdb.entities.Media
import com.sanstv.movies.databinding.ItemCastHeaderBinding
import com.sanstv.movies.databinding.ItemCastMetaBinding
import com.sanstv.movies.databinding.ItemHeadingBinding
import com.sanstv.movies.databinding.ItemListBinding

class CastDataAdapter(
    val setOnClickListener: (Media) -> Unit,
    val expandBiography: (String) -> Unit,
    val setOnImageClick: (String, ImageView) -> Unit
) : ListAdapter<CastDataModel, CastViewHolder>(CastDataModelDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): CastViewHolder {

        val headingViewHolder = CastViewHolder.HeadingViewHolder(
            itemBinding = ItemHeadingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

        val headerViewHolder = CastViewHolder.HeaderViewHolder(
            itemBinding = ItemCastHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ),
            castDataAdapter = this
        )

        val metaViewHolder = CastViewHolder.MetaViewHolder(
            itemBinding = ItemCastMetaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

        val listViewHolder = CastViewHolder.ListViewHolder(
            itemBinding = ItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ),
            castDataAdapter = this
        )

        return when (viewType) {
            R.layout.item_heading -> headingViewHolder
            R.layout.item_cast_header -> headerViewHolder
            R.layout.item_cast_meta -> metaViewHolder
            R.layout.item_list -> listViewHolder
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is CastViewHolder.HeadingViewHolder -> holder.bind(item as CastDataModel.Heading)
            is CastViewHolder.HeaderViewHolder -> holder.bind(item as CastDataModel.Header)
            is CastViewHolder.MetaViewHolder -> holder.bind(item as CastDataModel.Meta)
            is CastViewHolder.ListViewHolder -> {
                when (item) {
                    is CastDataModel.AppearsIn -> holder.bindAppearedIn(item)
                    else -> {}
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CastDataModel.Heading -> R.layout.item_heading
            is CastDataModel.Header -> R.layout.item_cast_header
            is CastDataModel.Meta -> R.layout.item_cast_meta
            is CastDataModel.AppearsIn -> R.layout.item_list
        }
    }
}