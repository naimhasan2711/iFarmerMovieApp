package com.nakibul.ifarmermovieapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nakibul.ifarmermovieapp.presentation.details.screen.DetailsScreen
import com.nakibul.ifarmermovieapp.presentation.home.screen.HomeScreen
import com.nakibul.ifarmermovieapp.presentation.splash.screen.SplashScreen
import com.nakibul.ifarmermovieapp.presentation.wishlist.screen.WishListScreen

@Composable
fun SetUpNavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {

        composable(
            route = Screen.Splash.route
        ) {
            SplashScreen(navController = navController)
        }

        composable(
            route = Screen.Home.route
        ) {
            HomeScreen()
        }

        composable(
            route = Screen.Details.route
        ) {
            DetailsScreen()
        }

        composable(
            route = Screen.Wishlist.route
        ) {
            WishListScreen()
        }
    }
}