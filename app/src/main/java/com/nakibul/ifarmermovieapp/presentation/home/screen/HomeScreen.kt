package com.nakibul.ifarmermovieapp.presentation.home.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nakibul.ifarmermovieapp.NetworkUtils
import com.nakibul.ifarmermovieapp.presentation.home.components.GenreFilterDropdown
import com.nakibul.ifarmermovieapp.presentation.home.components.MovieCard
import com.nakibul.ifarmermovieapp.presentation.home.components.WishlistBadge
import com.nakibul.ifarmermovieapp.presentation.splash.viewmodel.MoviesViewModel
import com.nakibul.ifarmermovieapp.ui.theme.Purple40

/**
 * Movie List Screen Composable
 * Displays a list of movies with search, filter, and wishlist functionality
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToDetails: (Int) -> Unit,
    onNavigateToWishlist: () -> Unit,
    viewModel: MoviesViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.state.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val pagedMovies by viewModel.pagedMovies.collectAsState()
    val isLoadingMore by viewModel.isLoadingMore.collectAsState()
    val favoriteMovies by viewModel.favoriteMovies.collectAsState()
    val listState = rememberLazyListState()

    var searchQuery by remember { mutableStateOf("") }
    var selectedGenre by remember { mutableStateOf<String?>(null) }
    var isSearchVisible by remember { mutableStateOf(false) }

    // Load favorite movies to ensure count is up-to-date
    LaunchedEffect(Unit) {
        viewModel.loadFavoriteMovies()
    }

    // Trigger search when searchQuery changes
    LaunchedEffect(searchQuery) {
        viewModel.searchMovies(searchQuery)
    }

    // Decide which movie list to show and apply genre filter
    val movieList = if (searchQuery.isNotBlank()) {
        searchResults
    } else {
        pagedMovies.let { movies ->
            if (selectedGenre != null) {
                movies.filter { movie ->
                    movie.genres.contains(selectedGenre) == true
                }
            } else {
                movies
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Movie App",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { isSearchVisible = !isSearchVisible }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    }

                    WishlistBadge(
                        count = favoriteMovies.size, // Use favoriteMovies.size instead of uiState.movieList.count
                        onClick = onNavigateToWishlist
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search Bar
            AnimatedVisibility(
                visible = isSearchVisible,
                enter = scaleIn(animationSpec = spring(stiffness = Spring.StiffnessHigh)),
                exit = scaleOut(animationSpec = tween(300))
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    placeholder = { Text("Search movies...") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    },
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            // Genre Filter
            GenreFilterDropdown(
                genres = uiState.genreList,
                selectedGenre = selectedGenre,
                onGenreSelected = { genre ->
                    selectedGenre = genre
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Movies List
            Box(modifier = Modifier.fillMaxSize()) {
                if (uiState.isLoading && uiState.movieList.isEmpty()) {
                    // Initial loading
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else if (movieList.isEmpty()) {
                    // Empty state
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No movies found, click here to fetch ",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .background(color = Purple40)
                                .padding(8.dp)
                                .clickable {
                                    if (NetworkUtils.isInternetAvailable(context)) {
                                        viewModel.fetchMovies()
                                        viewModel.getAllGenres()
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "No internet connection.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        )
                    }
                } else {
                    // Movies list
                    LazyColumn(
                        state = listState,
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = movieList,
                            key = { it.id }
                        ) { movie ->
                            MovieCard(
                                movie = movie,
                                onMovieClick = { onNavigateToDetails(movie.id) },
                                onFavoriteClick = { viewModel.toggleFavorite(movie.id) }
                            )
                        }

                        // Loading more indicator at the bottom
                        if (isLoadingMore) {
                            item {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.padding(16.dp)
                                    )
                                }
                            }
                        }
                    }

                    // Trigger loading more when scrolled to the end
                    LaunchedEffect(listState.firstVisibleItemIndex, movieList.size, isLoadingMore) {
                        val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                        if (
                            lastVisible != null &&
                            lastVisible >= movieList.size - 3 &&
                            searchQuery.isBlank() &&
                            !isLoadingMore
                        ) {
                            Log.d("HomeScreen", "Loading more movies...")
                            viewModel.loadMoreMovies()
                        }
                    }
                }
            }
        }
    }
}
