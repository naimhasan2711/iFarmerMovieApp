package com.nakibul.ifarmermovieapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakibul.ifarmermovieapp.domain.models.remote.Movie
import com.nakibul.ifarmermovieapp.domain.models.remote.MovieResponse
import com.nakibul.ifarmermovieapp.domain.use_case.FavoriteMovieUseCase
import com.nakibul.ifarmermovieapp.domain.use_case.FetchMoviesUseCase
import com.nakibul.ifarmermovieapp.domain.use_case.GenreUseCase
import com.nakibul.ifarmermovieapp.utils.Constant.PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val fetchMoviesUseCase: FetchMoviesUseCase,
    private val genreUseCase: GenreUseCase,
    private val favoriteMovieUseCase: FavoriteMovieUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MoviesState())
    val state: StateFlow<MoviesState> = _state.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Movie>>(emptyList())
    val searchResults: StateFlow<List<Movie>> = _searchResults.asStateFlow()

    private val _pagedMovies = MutableStateFlow<List<Movie>>(emptyList())
    val pagedMovies: StateFlow<List<Movie>> = _pagedMovies.asStateFlow()
    private var currentPage = 0
    private var endReached = false

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore = _isLoadingMore.asStateFlow()

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie = _selectedMovie.asStateFlow()

    private val _favoriteMovies = MutableStateFlow<List<Movie>>(emptyList())
    val favoriteMovies: StateFlow<List<Movie>> = _favoriteMovies.asStateFlow()

    /**
     * Initialize the ViewModel by fetching movies, genres, and loading initial movies.
     * This is called when the ViewModel is created.
     */
    init {
        fetchMovies()
        getAllGenres()
        loadInitialMovies()
        loadFavoriteMovies() // Add this line to load favorite movies at startup
    }

    /**
     * Fetch movies from local database or remote API.
     * If local cache is empty, it fetches from remote and updates the local database.
     */
    fun fetchMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.value = _state.value.copy(isLoading = true)
                val cached = fetchMoviesUseCase.getCachedMovies()
                if (cached.isEmpty()) {
                    val remote = fetchMoviesUseCase.fetchMovieResponse()
                    _state.value = _state.value.copy(
                        moviesResponse = remote,
                        isLoading = false,
                        errorMessage = "Cached data is empty, fetched from remote",
                        movieList = remote.movies,
                        genreList = remote.genres
                    )
                } else {
                    val movies = cached
                    _state.value = _state.value.copy(
                        moviesResponse = MovieResponse(
                            movies.flatMap { it.genres }.distinct(),
                            movies
                        ),
                        isLoading = false,
                        errorMessage = "",
                        movieList = movies,
                        genreList = movies.flatMap { it.genres }.distinct(),
                        genreList2 = genreUseCase.getCachedGenres()
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Unknown error"
                )
            }
        }
    }

    /**
     * Search movies based on the query.
     * If the query is blank, it returns an empty list.
     */
    fun searchMovies(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (query.isNotBlank()) {
                val result = fetchMoviesUseCase.searchMovies(query)
                _searchResults.value = result
            } else {
                _searchResults.value = emptyList()
            }
        }
    }

    /**
     * Fetch all genres from the remote API and update the state.
     * If an error occurs, it updates the error message in the state.
     */
    fun getAllGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val genres = genreUseCase.getAllGenres()
                _state.value = _state.value.copy(
                    genreList2 = genres
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    errorMessage = e.message ?: "Failed to fetch genres"
                )
            }
        }
    }

    /**
     * Load initial movies for pagination.
     * Resets the current page and endReached flag.
     */
    fun loadInitialMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isLoading = true)
            currentPage = 0
            endReached = false
            val movies = fetchMoviesUseCase.getMoviesPaged(PAGE_SIZE, 0)
            _pagedMovies.value = movies
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    /**
     * Load more movies for pagination.
     * It checks if the end is reached or if more movies are already loading.
     * If not, it fetches the next page of movies and updates the state.
     */
    fun loadMoreMovies() {
        if (endReached || _isLoadingMore.value) return
        viewModelScope.launch(Dispatchers.IO) {
            _isLoadingMore.value = true
            val nextOffset = (currentPage + 1) * PAGE_SIZE
            val movies = fetchMoviesUseCase.getMoviesPaged(PAGE_SIZE, nextOffset)
            if (movies.isEmpty()) {
                endReached = true
            } else {
                _pagedMovies.value = _pagedMovies.value + movies
                currentPage++
            }
            _isLoadingMore.value = false
        }
    }

    /**
     * Get a movie by its ID.
     * It first checks the paged movies and then the state movie list.
     * If not found, it fetches from the local database.
     */
    fun getMovieById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val movie = _pagedMovies.value.find { it.id == id }
                    ?: _state.value.movieList.find { it.id == id }
                    ?: fetchMoviesUseCase.getMovieById(id)

                movie?.let {
                    _selectedMovie.value = it
                } ?: run {
                    _selectedMovie.value = null
                }
            } catch (e: Exception) {
                _selectedMovie.value = null
            }
        }
    }

    /**
     * Toggle favorite status of a movie.
     * Updates both database and UI state.
     */
    fun toggleFavorite(movieId: Int) {
        viewModelScope.launch {
            // Perform database operation in IO dispatcher
            withContext(Dispatchers.IO) {
                favoriteMovieUseCase.toggleFavorite(movieId)
                // Update selected movie if it's the one being toggled
                _selectedMovie.value?.let {
                    if (it.id == movieId) {
                        val updatedMovie = fetchMoviesUseCase.getMovieById(movieId)
                        _selectedMovie.value = updatedMovie
                    }
                }
            }
            // After database operations are complete, update the UI with fresh data
            refreshCurrentPageMovies()
            loadFavoriteMovies()
        }
    }

    
    /**
     * Load all favorite movies
     */
    fun loadFavoriteMovies() {
        viewModelScope.launch {
            // Get favorites
            val favorites = withContext(Dispatchers.IO) {
                favoriteMovieUseCase.getFavoriteMovies()
            }
            // Update the state flow
            _favoriteMovies.value = favorites
        }
    }
    
    /**
     * Refresh the current page of movies to reflect favorite status changes
     */
    fun refreshCurrentPageMovies() {
        viewModelScope.launch {
            val currentMovieIds = _pagedMovies.value.map { it.id }
            
            val refreshed = withContext(Dispatchers.IO) {
                // If we have movies loaded, refresh those specific movies instead of paging
                if (currentMovieIds.isNotEmpty()) {
                    currentMovieIds.mapNotNull { id -> fetchMoviesUseCase.getMovieById(id) }
                } else {
                    // Otherwise refresh the current page as before
                    val offset = currentPage * PAGE_SIZE
                    fetchMoviesUseCase.getMoviesPaged(PAGE_SIZE, offset)
                }
            }
            _pagedMovies.value = refreshed
        }
    }
}
