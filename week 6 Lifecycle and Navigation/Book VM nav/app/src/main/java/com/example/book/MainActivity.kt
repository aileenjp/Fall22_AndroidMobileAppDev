package com.example.book

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.book.ui.compose.BookDetailScreen
import com.example.book.ui.compose.BookScreen
import com.example.book.ui.theme.BookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    ScreenSetup()
                }
            }
        }
    }

    @Composable
    fun ScreenSetup(){
        //BookScreen()
        
        val navController = rememberNavController()
        
        NavHost(navController = navController, startDestination = "books")
        {
            composable("books")
            {
                BookScreen(onNavigateToBookDetail = {bookName, author -> navController.navigate("bookDetail/$bookName/$author")})
//                BookScreen(onNavigateToBookDetail = {navController.navigate("bookDetail")})
            }
            composable("bookDetail/{bookName}/{author}", arguments = listOf(navArgument("bookName"){type= NavType.StringType}, navArgument("author"){type= NavType.StringType}))
            {
                backStackEntry ->
                BookDetailScreen(backStackEntry.arguments?.getString("bookName"), backStackEntry.arguments?.getString("author"))
            }
        }

    }
}

