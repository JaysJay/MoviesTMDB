package com.sanstv.movies.ui.shared_adapters.casts

import androidx.recyclerview.widget.RecyclerView

import com.sanstv.movies.R
import com.sanstv.movies.data.model.ProfileSize
import com.sanstv.movies.data.model.tmdb.entities.Cast
import com.sanstv.movies.databinding.ItemCastBinding
import com.sanstv.movies.ultility.Constants.TMDB_IMAGE_PREFIX
import com.sanstv.movies.ultility.GlideApp

class CastViewHolder(
    private val itemBinding: ItemCastBinding,
    val castAdapter: CastAdapter
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(cast: Cast) {
        val imageUrl = if (cast.profile_path != null) {
            "${TMDB_IMAGE_PREFIX}/${ProfileSize.w185}${cast.profile_path}"
        } else {
            R.drawable.no_actor
        }
        itemBinding.apply {
            GlideApp.with(actorImage)
                .load(imageUrl)
                .placeholder(R.drawable.no_actor)
                .into(actorImage)

            actorName.text = cast.name
            role.text = cast.character.split("/")[0]
            root.setOnClickListener {
                castAdapter.castOnClick.invoke(cast)
            }
        }
    }
}