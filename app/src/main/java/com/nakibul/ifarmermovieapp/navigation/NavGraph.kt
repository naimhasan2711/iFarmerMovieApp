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


        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToDetails = { movieId ->
                    navController.navigate(Screen.Details.route)
                },
                onNavigateToWishlist = {
                    navController.navigate(Screen.Wishlist.route)
                }
            )
        }

        composable(
            route = Screen.Details.route,
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: return@composable
            DetailsScreen(
            )
        }

        composable(
            route = Screen.Wishlist.route
        ) {
            WishListScreen()
        }
    }
}