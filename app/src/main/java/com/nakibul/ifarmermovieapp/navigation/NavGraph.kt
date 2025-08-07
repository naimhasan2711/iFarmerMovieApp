package com.nakibul.ifarmermovieapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nakibul.ifarmermovieapp.presentation.ui.details.screen.DetailsScreen
import com.nakibul.ifarmermovieapp.presentation.ui.home.screen.HomeScreen
import com.nakibul.ifarmermovieapp.presentation.ui.splash.SplashScreen
import com.nakibul.ifarmermovieapp.presentation.ui.wishList.WishListScreen
import com.nakibul.ifarmermovieapp.utils.Constant.MOVIE_ID

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
                    navController.navigate(Screen.Details.createRoute(movieId))
                },
                onNavigateToWishlist = {
                    navController.navigate(Screen.Wishlist.route)
                }
            )
        }

        composable(
            route = Screen.Details.route,
            arguments = listOf(
                navArgument(MOVIE_ID) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt(MOVIE_ID) ?: -1
            DetailsScreen(movieId = movieId) {
                navController.navigate(Screen.Home.route)
            }
        }

        composable(
            route = Screen.Wishlist.route
        ) {
            WishListScreen(
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }
    }
}
