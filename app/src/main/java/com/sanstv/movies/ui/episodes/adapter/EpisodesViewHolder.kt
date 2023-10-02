package com.sanstv.movies.ui.episodes.adapter

import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

import com.sanstv.movies.R
import com.sanstv.movies.data.model.PosterSize
import com.sanstv.movies.data.model.StillSize
import com.sanstv.movies.data.model.tmdb.entities.Episode
import com.sanstv.movies.databinding.ItemEpisodeBinding
import com.sanstv.movies.databinding.ItemEpisodeHeaderBinding
import com.sanstv.movies.ultility.Constants.TMDB_IMAGE_PREFIX
import com.sanstv.movies.ultility.GlideApp
import com.sanstv.movies.ultility.util.SpannableTextView


sealed class EpisodesViewHolder(
    binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    class HeaderViewHolder(
        private val itemBinding: ItemEpisodeHeaderBinding
    ) : EpisodesViewHolder(itemBinding) {
        fun bind(item: EpisodesDataModel.Header) {
            val posterUrl = if (item.seasonPosterPath != null) {
                "${TMDB_IMAGE_PREFIX}/${PosterSize.w780}${item.seasonPosterPath}"
            } else R.drawable.no_poster

            val overviewText = item.seasonOverview.ifEmpty { "No description" }

            itemBinding.apply {
                tvSeasonNumber.text = item.seasonNumber
                tvPlot.text = overviewText

                val tvSeasonNameTAG = "tvSeasonNameTAG"

                if (item.seasonName.isNullOrEmpty() || item.seasonName == item.seasonNumber) {
                    tvSeasonName.tag = tvSeasonNameTAG
                    tvSeasonName.isGone = true
                } else {
                    tvSeasonName.tag = null
                    tvSeasonName.text = item.seasonName
                }

                tvSeasonName.isGone = tvSeasonName.tag == tvSeasonNameTAG

                GlideApp.with(ivPoster)
                    .load(posterUrl)
                    .placeholder(R.drawable.no_poster)
                    .into(ivPoster)
            }
        }
    }

    class EpisodeViewHolder(
        private val itemBinding: ItemEpisodeBinding,
        val episodesDataAdapter: EpisodesDataAdapter
    ) : EpisodesViewHolder(itemBinding) {
        fun bind(episode: EpisodesDataModel.Episode) {

            val episodeStillUrl = if (episode.stillPath.isNullOrEmpty()) {
                R.drawable.no_thumb
            } else {
                "${TMDB_IMAGE_PREFIX}/${StillSize.original}${episode.stillPath}"
            }

            val count = "Episode ${episode.episodeNumber}"
            val title = episode.name.ifEmpty { "No title" }

            itemBinding.apply {
                val ivThumbTAG = "ivThumbTAG"
                val tvEpisodeCountTAG = "tvEpisodeCountTAG"

                if (episode.stillPath.isNullOrEmpty() || ivThumb.tag == ivThumbTAG) {
                    ivThumb.tag = ivThumbTAG
                    ivThumb.isGone = true
                } else {
                    ivThumb.tag = null
                    GlideApp.with(ivThumb)
                        .asBitmap()
                        .load(episodeStillUrl)
                        .placeholder(R.drawable.no_thumb)
                        .into(ivThumb)
                }

                if (count == title || tvEpisodeCount.tag == tvEpisodeCountTAG) {
                    tvEpisodeCount.tag = tvEpisodeCountTAG
                    tvEpisodeCount.isInvisible = true
                } else {
                    tvEpisodeCount.tag = null
                    tvEpisodeCount.text = count
                }

                val overviewText = if (episode.overview.isNullOrEmpty()) {
                    "No description"
                } else episode.overview

                SpannableTextView.spannablePlotText(
                    tvOverview, overviewText, 180, "...more", root
                )
                tvTitle.text = title
                root.setOnClickListener {
                    episodesDataAdapter.episodeOnClick.invoke(
                        Episode(
                            id = episode.id,
                            name = episode.name,
                            overview = episode.overview,
                            episode_number = episode.episodeNumber,
                            season_number = episode.seasonNumber,
                            still_path = episode.stillPath,
                            guest_stars = null
                        )
                    )
                }
            }
        }

    }
}