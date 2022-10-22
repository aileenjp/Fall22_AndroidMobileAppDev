package com.example.helloandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Greeting(stringResource(R.string.scotty))
        }
    }
}

@Composable
fun Greeting(name: String) {
    Column {
        Image(
            painter = painterResource(id = R.drawable.scottydog),
            contentDescription = stringResource(id = R.string.scotty),
            modifier = Modifier
                .padding(top = 40.dp, bottom = 40.dp)
                .size(190.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally)

//            modifier = Modifier
//                .padding(top = 40.dp, bottom = 40.dp)
//                .height(190.dp)
//                .fillMaxWidth()

        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color.Black)
        ) {
        Text(
            stringResource(R.string.greeting) + " " + name,
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(Color.Black)
//                .padding(top = 20.dp, bottom = 20.dp),
            color = Color.Red,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Greeting(stringResource(R.string.scotty))
}