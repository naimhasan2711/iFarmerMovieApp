package com.nakibul.ifarmermovieapp.domain.use_case

import com.nakibul.ifarmermovieapp.data.local.GenreDao
import com.nakibul.ifarmermovieapp.domain.models.local.Genre
import com.nakibul.ifarmermovieapp.domain.repository.MovieRepository
import javax.inject.Inject

class GenreUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val genreDao: GenreDao
) {
    suspend fun getAllGenres() = movieRepository.getAllGenres()
    suspend fun getCachedGenres(): List<Genre> = genreDao.getAllGenres()
}
