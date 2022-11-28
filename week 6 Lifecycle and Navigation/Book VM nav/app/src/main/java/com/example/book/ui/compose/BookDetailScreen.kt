package com.example.book.ui.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.book.ui.theme.BookTheme

@Composable
fun BookDetailScreen(bookName: String? = "Book", bookAuthor: String? = "Author"){
    Surface(
    ) {
        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(10.dp),
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary,
            border = BorderStroke(2.dp, color = MaterialTheme.colors.primaryVariant),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                bookName?.let { Text(text = it, style = MaterialTheme.typography.h6) }
                bookAuthor?.let { Text(text = it, style = MaterialTheme.typography.body1) }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    BookTheme {
        BookDetailScreen()
    }
}