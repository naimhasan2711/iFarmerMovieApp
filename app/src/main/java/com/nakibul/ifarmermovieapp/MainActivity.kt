package com.nakibul.ifarmermovieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nakibul.ifarmermovieapp.navigation.SetUpNavGraph
import com.nakibul.ifarmermovieapp.presentation.viewmodel.ThemeViewModel
import com.nakibul.ifarmermovieapp.ui.theme.IFarmerMovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val themeViewModel: ThemeViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
            
            IFarmerMovieAppTheme(darkTheme = isDarkTheme) {
                navController = rememberNavController()
                SetUpNavGraph(navController = navController)
            }
        }
    }
}
