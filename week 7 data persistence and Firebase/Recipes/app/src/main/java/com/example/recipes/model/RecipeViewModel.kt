package com.example.recipes.model

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipes.util.RecipeDatabase
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeViewModel: ViewModel() {
    private val recipedb = RecipeDatabase()

    var recipeList = recipedb.recipes

    init {
        viewModelScope.launch(Dispatchers.IO) { recipedb.getRecipes() }
    }

    fun addRecipe(recipe: Recipe) = viewModelScope.launch(Dispatchers.IO){
        recipedb.addRecipe(recipe)
    }

    fun deleteRecipe(id: String) = viewModelScope.launch(Dispatchers.IO){
        recipedb.deleteRecipe(id)
    }

}
