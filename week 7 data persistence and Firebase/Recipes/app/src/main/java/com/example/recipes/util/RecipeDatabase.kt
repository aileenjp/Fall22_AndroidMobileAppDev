package com.example.recipes.util

import android.os.Build.ID
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.recipes.model.Recipe
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase

class RecipeDatabase {
    //Firestore instance
    private val db = Firebase.firestore

    //recipe collection
    private val recipeRef = db.collection("recipes")

    var recipes = mutableStateListOf<Recipe>()

    suspend fun getRecipes() {
         try {
            recipeRef.addSnapshotListener { docSnapShot, error ->
                if (docSnapShot != null) {
                    //clear list to avoid duplicates
                    recipes.clear()
                    for (doc in docSnapShot) {
                        //add to list
                        recipes.add(doc.toObject())
                        Log.d("get", doc.getId())
                    }
                    Log.d("get", recipes.size.toString())
                } else
                    if (error != null) {
                        Log.e("get", "listener error", error)
                    }
            }
        } catch (error: FirebaseFirestoreException) {
            Log.e("get", "listener error", error)
        }
    }

//    fun addRecipe(recipe: Recipe) {
//        try {
//            val docRef = recipeRef.add(recipe)
//            Log.d("add", "Document written")
//        } catch (error: FirebaseFirestoreException) {
//            Log.e("add", "Error adding document", error)
//        }
//    }

    suspend fun addRecipe(recipe: Recipe){
        recipeRef.add(recipe)
            .addOnSuccessListener { documentReference ->
                Log.d("add", "DocumentSnapshot written with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.e("add", "Error adding document", e)
            }
    }

    suspend fun deleteRecipe(id: String){
        recipeRef.document(id).delete()
            .addOnSuccessListener {
                Log.d("delete", "DocumentSnapshot successfully deleted")
            }
            .addOnFailureListener { e ->
                Log.e("delete", "Error deleting document", e)
            }
    }

}
