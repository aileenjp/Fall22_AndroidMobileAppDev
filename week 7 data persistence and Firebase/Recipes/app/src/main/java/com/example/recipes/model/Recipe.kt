package com.example.recipes.model

import com.google.firebase.firestore.DocumentId

data class Recipe(@DocumentId val id: String, val name: String, val url:String) {
    constructor(): this( "", "", ""){}
}
