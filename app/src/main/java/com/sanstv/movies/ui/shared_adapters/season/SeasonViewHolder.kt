package com.sanstv.movies.ui.shared_adapters.season

import androidx.recyclerview.widget.RecyclerView

import com.sanstv.movies.R
import com.sanstv.movies.data.model.PosterSize
import com.sanstv.movies.data.model.tmdb.entities.Season
import com.sanstv.movies.databinding.ItemDetailedMediaBinding
import com.sanstv.movies.ultility.Constants.TMDB_IMAGE_PREFIX
import com.sanstv.movies.ultility.GlideApp
import com.sanstv.movies.ultility.util.Converter

class SeasonViewHolder(
    private val showName: String,
    private val itemBinding: ItemDetailedMediaBinding,
    val seasonsAdapter: SeasonsAdapter
) : RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(season: Season) {
        itemBinding.apply {
            val seasonPosterUrl = if (season.poster_path == null) {
                R.drawable.no_poster
            } else {
                "${TMDB_IMAGE_PREFIX}/${PosterSize.w342}${season.poster_path}"
            }

            GlideApp.with(ivPoster)
                .load(seasonPosterUrl)
                .placeholder(R.drawable.no_poster)
                .into(ivPoster)

            val seasonName = "Season ${season.season_number}"
            tvTitle.text = season.name

            var premiered = "$seasonName of $showName"
            var yearSeason = ""

            val formattedDate = season.air_date?.let { date ->
                yearSeason += "${date.take(4)} | "
                Converter.parseDate(date)
            }

            yearSeason += "${season.episode_count} episodes"
            tvYear.text = yearSeason

            formattedDate?.let {
                premiered += " premiered on $formattedDate."
            }
            val seasonPlot = if (season.overview.toString() == "") {
                premiered
            } else season.overview
            tvPlot.text = seasonPlot
            root.setOnClickListener {
                seasonsAdapter.seasonOnClick.invoke(season)
            }
        }
    }
}
