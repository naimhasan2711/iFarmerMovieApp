package com.nakibul.ifarmermovieapp.navigation

sealed class Screen(val route: String) {
    object Splash : Screen(route = "splash_screen")
    object Home : Screen(route = "home_screen")
    object Details : Screen(route = "details_screen")
    object Wishlist : Screen(route = "wishlist_screen")
}