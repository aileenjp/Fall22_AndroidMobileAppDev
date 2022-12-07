package com.example.recipes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import com.example.recipes.screens.RecipeScreen
import com.example.recipes.ui.theme.RecipesTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    ScreenSetup(loadWebPage = ::startDetailActivity, login = {login()}, logout = {logout()})
                    }
            }
        }
    }

    fun startDetailActivity(url: String = "https://www.google.com") {
        //Log.d("web start detail", url)
        var intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    fun login() {
        val intent = Intent(this, GoogleSignInActivity::class.java)
        intent.putExtra("googleAuth", "login")
        startActivity(intent)
    }

    fun logout(){
        val intent = Intent(this, GoogleSignInActivity::class.java)
        intent.putExtra("googleAuth", "logout")
        startActivity(intent)
    }
}

@Composable
fun ScreenSetup(loadWebPage: (String) -> Unit, login: () -> Unit, logout: () -> Unit){
    RecipeScreen(loadWebPage = loadWebPage, login, logout)
//    RecipeScreen(loadWebPage = loadWebPage)
}
