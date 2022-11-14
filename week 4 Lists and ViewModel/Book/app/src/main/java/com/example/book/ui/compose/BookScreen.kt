package com.example.book.ui.compose

import android.content.Context
import android.graphics.Color
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.book.R
import com.example.book.model.Book
import com.example.book.model.sampleBookData
import com.example.book.ui.theme.BookTheme

@Composable
fun BookScreen() {
    val context = LocalContext.current
    LazyColumn(
        contentPadding = PaddingValues(
        vertical = 8.dp,
        horizontal = 8.dp)
    )
    {
        items(sampleBookData){book ->
            BookItem(item = book, context)
        }
    }
}

@Composable
fun BookItem(item: Book, context: Context) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        border = BorderStroke(2.dp, color = MaterialTheme.colors.primaryVariant),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                Toast
                    .makeText(
                        context,
                        context.resources.getString(R.string.readmsg) + " " + item.bookName,
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = item.bookName, style = MaterialTheme.typography.h6)
            Text(text = item.author, style = MaterialTheme.typography.body1)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BookTheme {
        BookScreen()
    }
}