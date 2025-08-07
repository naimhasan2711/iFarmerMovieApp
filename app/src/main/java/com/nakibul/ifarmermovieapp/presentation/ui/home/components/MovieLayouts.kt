package com.nakibul.ifarmermovieapp.presentation.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nakibul.ifarmermovieapp.domain.models.remote.Movie

/**
 * Represents the available layout modes for displaying movies
 */
enum class MovieLayoutMode {
    LIST,
    GRID
}

/**
 * Displays movies in a vertical list layout
 */
@Composable
fun MovieList(
    movies: List<Movie>,
    isLoadingMore: Boolean,
    onMovieClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    onLoadMore: () -> Unit,
    searchQuery: String
) {
    val listState = rememberLazyListState()
    
    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = movies,
            key = { it.id }
        ) { movie ->
            MovieCard(
                movie = movie,
                onMovieClick = { onMovieClick(movie.id) },
                onFavoriteClick = { onFavoriteClick(movie.id) }
            )
        }

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

    LaunchedEffect(listState.firstVisibleItemIndex, movies.size, isLoadingMore) {
        val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        if (
            lastVisible != null &&
            lastVisible >= movies.size - 3 &&
            searchQuery.isBlank() &&
            !isLoadingMore
        ) {
            onLoadMore()
        }
    }
}

/**
 * Displays movies in a 2-column grid layout with equal height cells
 */
@Composable
fun MovieGrid(
    movies: List<Movie>,
    isLoadingMore: Boolean,
    onMovieClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    onLoadMore: () -> Unit,
    searchQuery: String
) {
    val gridState = rememberLazyGridState()
    
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = gridState,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            count = movies.size,
            key = { movies[it].id }
        ) { index ->
            val movie = movies[index]
            MovieGridItem(
                movie = movie,
                onMovieClick = { onMovieClick(movie.id) },
                onFavoriteClick = { onFavoriteClick(movie.id) }
            )
        }
        
        if (isLoadingMore) {
            item(span = { GridItemSpan(2) }) {
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
    
    LaunchedEffect(gridState.firstVisibleItemIndex, movies.size, isLoadingMore) {
        val lastVisible = gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        if (
            lastVisible != null &&
            lastVisible >= movies.size - 3 &&
            searchQuery.isBlank() &&
            !isLoadingMore
        ) {
            onLoadMore()
        }
    }
}
