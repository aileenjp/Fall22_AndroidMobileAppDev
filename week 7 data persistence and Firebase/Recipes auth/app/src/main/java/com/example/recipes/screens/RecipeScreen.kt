package com.example.recipes.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipes.model.RecipeViewModel
import com.example.recipes.ui.theme.RecipesTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipes.R
import com.example.recipes.model.Recipe
import java.util.*


@Composable
fun RecipeScreen(loadWebPage: (String) -> Unit, login: () -> Unit, logout: () -> Unit) {
//fun RecipeScreen(loadWebPage: (String) -> Unit) {
    val viewModel: RecipeViewModel = viewModel()
    val context = LocalContext.current
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        backgroundColor = MaterialTheme.colors.surface,
        topBar = { AppBar(login, logout) },
        // topBar = { AppBar({}, {}) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {showAddDialog = true}
            )
            {
                Icon(Icons.Filled.Add, contentDescription = "")
            }
        },
        content = {
            if (showAddDialog){
                addRecipeDialog(context, dismissDialog = {showAddDialog = false}, {viewModel.addRecipe(it)})
            }
            LazyColumn(
                contentPadding = PaddingValues(
                    vertical = 8.dp,
                    horizontal = 8.dp
                )
            )
            {
                items(viewModel.recipeList, key={recipe -> recipe.id}) { recipe ->
                    RecipeItem(item = recipe, context, {viewModel.deleteRecipe(it)}, loadWebPage=loadWebPage)
                }
            }
        }
    )
}

@Composable
fun addRecipeDialog(context: Context, dismissDialog:() -> Unit, addBook: (Recipe) -> Unit){
    var nameTextField by remember {
        mutableStateOf("")
    }
    var urlTextField by remember {
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest = { dismissDialog},
        title={ Text(text = stringResource(id = com.example.recipes.R.string.addRecipe), style = MaterialTheme.typography.h6) },
        text = {
            Column(modifier = Modifier.padding(top=20.dp)) {
                TextField(label = { Text(text= stringResource(id = com.example.recipes.R.string.recipeName)) }, value = nameTextField, onValueChange = {nameTextField=it})
                Spacer(modifier = Modifier.height(10.dp))
                TextField(label = { Text(text= stringResource(id = com.example.recipes.R.string.recipeURL)) },value = urlTextField, onValueChange = {urlTextField=it})
            }
        },
        confirmButton = {
            Button(onClick = {
                if(nameTextField.isNotEmpty()) {
                    addBook(Recipe("",nameTextField, urlTextField))
                    Toast.makeText(
                        context,
                        context.resources.getString(com.example.recipes.R.string.recipeAdded),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                dismissDialog()
            })
            {
                Text(text = stringResource(id = com.example.recipes.R.string.add))
            }
        },dismissButton = {
            Button(onClick = {
                dismissDialog()
            }) {
                Text(text = stringResource(id = com.example.recipes.R.string.cancel))
            }
        }
    )
}

@Composable
fun deleteRecipeDialog(context: Context, dismissDialog:() -> Unit, item: Recipe, deleteBook: (String) -> Unit){
    AlertDialog(
        onDismissRequest = { dismissDialog},
        title={ Text(text = stringResource(id = R.string.delete), style = MaterialTheme.typography.h6) },
        confirmButton = {
            Button(onClick = {
                deleteBook(item.id)
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.deleteRecipe),
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecipeItem(item: Recipe, context: Context, deleteBook: (String) -> Unit, loadWebPage: (String) -> Unit) {
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
                    // Log.d("web book item", item.url)
                    loadWebPage(item.url)
                },
                onLongClick = { showDeleteDialog = true }
            )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = item.name, style = MaterialTheme.typography.h6)
        }

    }
    if (showDeleteDialog){
        deleteRecipeDialog(context, dismissDialog = {showDeleteDialog = false}, item, deleteBook)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RecipesTheme {
        RecipeScreen({}, {}, {})
        //RecipeScreen({})
    }
}