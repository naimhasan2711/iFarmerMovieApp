package com.nakibul.ifarmermovieapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakibul.ifarmermovieapp.domain.repository.UserPreferencesRepository
import com.nakibul.ifarmermovieapp.presentation.ui.home.components.MovieLayoutMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    
    val isDarkTheme: StateFlow<Boolean> = userPreferencesRepository.isDarkTheme
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )
    
    val layoutMode: StateFlow<MovieLayoutMode> = userPreferencesRepository.layoutMode
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MovieLayoutMode.LIST
        )
    
    fun toggleTheme() {
        viewModelScope.launch {
            userPreferencesRepository.setDarkTheme(!isDarkTheme.value)
        }
    }
    
    fun setLayoutMode(mode: MovieLayoutMode) {
        viewModelScope.launch {
            userPreferencesRepository.setLayoutMode(mode)
        }
    }
    
    fun toggleLayoutMode() {
        val newMode = if (layoutMode.value == MovieLayoutMode.LIST) {
            MovieLayoutMode.GRID
        } else {
            MovieLayoutMode.LIST
        }
        setLayoutMode(newMode)
    }
}
