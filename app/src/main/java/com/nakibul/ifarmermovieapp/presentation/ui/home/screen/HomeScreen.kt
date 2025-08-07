package com.nakibul.ifarmermovieapp.presentation.ui.home.screen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nakibul.ifarmermovieapp.R
import com.nakibul.ifarmermovieapp.presentation.ui.home.components.GenreFilterDropdown
import com.nakibul.ifarmermovieapp.presentation.ui.home.components.MovieGrid
import com.nakibul.ifarmermovieapp.presentation.ui.home.components.MovieLayoutMode
import com.nakibul.ifarmermovieapp.presentation.ui.home.components.MovieList
import com.nakibul.ifarmermovieapp.presentation.ui.home.components.WishlistBadge
import com.nakibul.ifarmermovieapp.presentation.viewmodel.MoviesViewModel
import com.nakibul.ifarmermovieapp.presentation.viewmodel.ThemeViewModel
import com.nakibul.ifarmermovieapp.ui.theme.Purple40
import com.nakibul.ifarmermovieapp.utils.NetworkUtils

/**
 * Movie List Screen Composable
 * Displays a list of movies with search, filter, and wishlist functionality
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToDetails: (Int) -> Unit,
    onNavigateToWishlist: () -> Unit,
    viewModel: MoviesViewModel = hiltViewModel(),
    themeViewModel: ThemeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.state.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val pagedMovies by viewModel.pagedMovies.collectAsState()
    val isLoadingMore by viewModel.isLoadingMore.collectAsState()
    val favoriteMovies by viewModel.favoriteMovies.collectAsState()
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
    val layoutMode by themeViewModel.layoutMode.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var selectedGenre by remember { mutableStateOf<String?>(null) }
    var isSearchVisible by remember { mutableStateOf(false) }

    // Load favorite movies to ensure count is up-to-date
    LaunchedEffect(Unit) {
        viewModel.loadFavoriteMovies()
    }
    
    // refresh the current page
    LaunchedEffect(true) {
        viewModel.refreshCurrentPageMovies()
    }

    // Trigger search when searchQuery changes
    LaunchedEffect(searchQuery) {
        viewModel.searchMovies(searchQuery)
    }

    // apply genre filter
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
                    // Layout toggle button
                    IconButton(onClick = { 
                        themeViewModel.toggleLayoutMode()
                    }) {
                        Icon(
                            painter = if (layoutMode == MovieLayoutMode.LIST)
                                painterResource(R.drawable.ic_grid) else painterResource(R.drawable.ic_list),
                            contentDescription = if (layoutMode == MovieLayoutMode.LIST) 
                                "Switch to Grid View" else "Switch to List View"
                        )
                    }
                    
                    IconButton(onClick = { isSearchVisible = !isSearchVisible }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    }

                    // Theme toggle icon
                    IconButton(onClick = { themeViewModel.toggleTheme() }) {
                        Icon(
                            painter = if (isDarkTheme) painterResource(id = R.drawable.ic_light) else painterResource(id = R.drawable.ic_dark),
                            contentDescription = if (isDarkTheme) "Switch to Light Theme" else "Switch to Dark Theme"
                        )
                    }

                    WishlistBadge(
                        count = favoriteMovies.size,
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
                    // Choose between list or grid layout based on saved preference
                    when (layoutMode) {
                        MovieLayoutMode.LIST -> {
                            MovieList(
                                movies = movieList,
                                isLoadingMore = isLoadingMore,
                                onMovieClick = onNavigateToDetails,
                                onFavoriteClick = { viewModel.toggleFavorite(it) },
                                onLoadMore = { viewModel.loadMoreMovies() },
                                searchQuery = searchQuery
                            )
                        }
                        MovieLayoutMode.GRID -> {
                            MovieGrid(
                                movies = movieList,
                                isLoadingMore = isLoadingMore,
                                onMovieClick = onNavigateToDetails,
                                onFavoriteClick = { viewModel.toggleFavorite(it) },
                                onLoadMore = { viewModel.loadMoreMovies() },
                                searchQuery = searchQuery
                            )
                        }
                    }
                }
            }
        }
    }
}
