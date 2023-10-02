package com.sanstv.movies.data.model.tmdb.person

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.sanstv.movies.ultility.util.Converter

@Keep
data class PersonResponse(
    @field:SerializedName("combined_credits")
    val combined_credits: CombinedCredits,
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("imdb_id")
    val imdb_id: String?,
    @field:SerializedName("biography")
    val biography: String?,
    @field:SerializedName("birthday")
    val birthday: String?,
    @field:SerializedName("deathday")
    val deathday: String?,
    @field:SerializedName("gender")
    val gender: Int?,
    @field:SerializedName("name")
    val name: String?,
    @field:SerializedName("place_of_birth")
    val place_of_birth: String?,
    @field:SerializedName("profile_path")
    val profile_path: String?
) {
    val genderName
        get() = when (gender) {
            0 -> "Others"
            1 -> "Female"
            2 -> "Male"
            else -> "Unknown"
        }

    fun age(): Int? {
        return if (birthday != null) {
            if (deathday != null) {
                Converter.yearsBetween(birthday, deathday)
            } else {
                Converter.yearsBetween(birthday, Converter.getDate())
            }
        } else null
    }
}