@file:OptIn(ExperimentalFoundationApi::class)

package com.example.book.ui.compose

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.book.R
import com.example.book.model.Book
import com.example.book.model.BookViewModel
import com.example.book.ui.theme.BookTheme
import java.util.*

@Composable
fun BookScreen() {
    val viewModel: BookViewModel = viewModel()
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        backgroundColor = MaterialTheme.colors.surface,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {showDialog = true}
            )
            {
                Icon(Icons.Filled.Add, contentDescription = "")
            }
        },
        content = {
            if (showDialog){
                addBookDialog(context, dismissDialog = {showDialog = false}, {viewModel.add(it)})
            }
            LazyColumn(
                contentPadding = PaddingValues(
                    vertical = 8.dp,
                    horizontal = 8.dp
                )
            )
            {
                items(viewModel.bookList, key={book -> book.id}) { book ->
                    BookItem(item = book, context, {viewModel.delete(it)})
                }
            }
        }
    )
}

@Composable
fun addBookDialog(context: Context, dismissDialog:() -> Unit, addBook: (Book) -> Unit){
    var bookTextField by remember {
        mutableStateOf("")
    }
    var authorTextField by remember {
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest = { dismissDialog},
        title={Text(text = stringResource(id = R.string.addBook), style = MaterialTheme.typography.h6)},
        text = {
            Column(modifier = Modifier.padding(top=20.dp)) {
                TextField(label = {Text(text=stringResource(id = R.string.bookName))}, value = bookTextField, onValueChange = {bookTextField=it})
                Spacer(modifier = Modifier.height(10.dp))
                TextField(label = {Text(text=stringResource(id = R.string.authorName))},value = authorTextField, onValueChange = {authorTextField=it})
            }
        },
        confirmButton = {
            Button(onClick = {
                if(bookTextField.isNotEmpty()) {
                    val newID = UUID.randomUUID().toString();
                    addBook(Book(newID, bookTextField, authorTextField))
                    Toast.makeText(
                        context,
                        context.resources.getString(R.string.bookAdded),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                dismissDialog()
            })
            {
                Text(text = stringResource(id = R.string.add))
            }
        },dismissButton = {
            Button(onClick = {
                dismissDialog()
            }) {
                Text(text = stringResource(id = R.string.cancel))
            }
        }
    )
}

@Composable
fun deleteBookDialog(context: Context, dismissDialog:() -> Unit, item: Book, deleteBook: (Book) -> Unit){
    AlertDialog(
        onDismissRequest = { dismissDialog},
        title={Text(text = stringResource(id = R.string.delete), style = MaterialTheme.typography.h6)},
        confirmButton = {
            Button(onClick = {
                deleteBook(item)
                    Toast.makeText(
                        context,
                        context.resources.getString(R.string.deleteBook),
                        Toast.LENGTH_SHORT
                    ).show()
                dismissDialog()
            })
            {
                Text(text = stringResource(id = R.string.yes))
            }
        },dismissButton = {
            Button(onClick = {
                dismissDialog()
            }) {
                Text(text = stringResource(id = R.string.no))
            }
        }
    )
}

@Composable
fun BookItem(item: Book, context: Context, deleteBook: (Book) -> Unit) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        border = BorderStroke(2.dp, color = MaterialTheme.colors.primaryVariant),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    Toast
                        .makeText(
                            context,
                            context.resources.getString(R.string.readmsg) + " " + item.bookName,
                            Toast.LENGTH_SHORT
                        )
                        .show()
                },
                onLongClick = { showDeleteDialog = true }
            )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = item.bookName, style = MaterialTheme.typography.h6)
            Text(text = item.author, style = MaterialTheme.typography.body1)
        }

    }
    if (showDeleteDialog){
        deleteBookDialog(context, dismissDialog = {showDeleteDialog = false}, item, deleteBook)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BookTheme {
        BookScreen()
    }
}