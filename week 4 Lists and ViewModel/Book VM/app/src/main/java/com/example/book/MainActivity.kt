package com.example.book

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.book.model.BookViewModel
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
        BookScreen()
    }
}

