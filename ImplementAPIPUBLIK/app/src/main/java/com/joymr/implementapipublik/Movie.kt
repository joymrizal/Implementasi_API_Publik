package com.joymr.implementapipublik

data class Movie(
    val adult: Boolean,
    val backdrop_path: String?,
    val genres: List<Genre>,
    val id: Int,
    val original_title: String,
    val overview: String,
    val poster_path: String?,
    val release_date: String,
    val title: String,
    val vote_average: Double
)

data class Genre(
    val id: Int,
    val name: String
)