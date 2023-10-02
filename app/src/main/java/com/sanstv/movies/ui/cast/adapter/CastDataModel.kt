package com.sanstv.movies.ui.cast.adapter

import androidx.annotation.Keep
import com.sanstv.movies.data.model.tmdb.entities.Media

sealed class CastDataModel {

    @Keep
    data class Heading(
        val heading: String
    ) : CastDataModel()

    @Keep
    data class Header(
        val name: String?,
        val biography: String?,
        val profilePath: String?
    ) : CastDataModel()

    @Keep
    data class Meta(
        val id: Int,
        val age: Int?,
        val birthday: String?,
        val death: String?,
        val gender: Int?,
        val genderName: String,
        val place_of_birth: String?
    ) : CastDataModel()

    @Keep
    data class AppearsIn(
        val appearsIn: List<Media>
    ) : CastDataModel()

}