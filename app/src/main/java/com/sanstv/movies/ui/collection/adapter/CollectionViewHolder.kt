package com.sanstv.movies.ui.collection.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

import com.sanstv.movies.R
import com.sanstv.movies.data.model.BackdropSize
import com.sanstv.movies.data.model.PosterSize
import com.sanstv.movies.databinding.ItemCollectionHeaderBinding
import com.sanstv.movies.databinding.ItemListBinding
import com.sanstv.movies.ui.shared_adapters.detailed_media.DetailedMediaAdapter
import com.sanstv.movies.ultility.Constants.TMDB_IMAGE_PREFIX
import com.sanstv.movies.ultility.GlideApp


sealed class CollectionViewHolder(
    binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    class HeaderViewHolder(
        private val itemBinding: ItemCollectionHeaderBinding
    ) : CollectionViewHolder(itemBinding) {
        fun bind(item: CollectionDataModel.Header) {
            val backdropUrl = if (item.backdropPath == null) {
                if (item.posterPath == null) {
                    "${TMDB_IMAGE_PREFIX}/${PosterSize.w780}${item.posterPath}"
                } else R.drawable.no_thumb
            } else {
                "${TMDB_IMAGE_PREFIX}/${BackdropSize.w780}${item.backdropPath}"
            }

            itemBinding.apply {
                tvName.text = item.title
                GlideApp.with(ivBackdrop)
                    .load(backdropUrl)
                    .placeholder(R.drawable.no_thumb)
                    .into(ivBackdrop)
            }
        }
    }

    class ListViewHolder(
        private val itemBinding: ItemListBinding,
        collectionDataAdapter: CollectionDataAdapter
    ) : CollectionViewHolder(itemBinding) {

        private val detailedMediaBinding by lazy {
            DetailedMediaAdapter {
                collectionDataAdapter.setOnClickListener.invoke(it)
            }
        }

        fun bindParts(item: CollectionDataModel.Parts) {
            itemBinding.rvList.apply {
                adapter = detailedMediaBinding
                layoutManager = LinearLayoutManager(
                    context, LinearLayoutManager.VERTICAL, false
                )
            }
            detailedMediaBinding.submitList(item.parts)
        }
    }
}