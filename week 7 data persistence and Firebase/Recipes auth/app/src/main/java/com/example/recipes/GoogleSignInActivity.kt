package com.example.recipes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class GoogleSignInActivity : ComponentActivity() {
    //main entry for Firebase authentication
    private val auth: FirebaseAuth = Firebase.auth
    //Google signin API
    private lateinit var googleSignInClient: GoogleSignInClient
    //launch an activity with a result
    private lateinit var authResultLauncher: ActivityResultLauncher<Intent>
    //Firebase user
    private var currentUser by mutableStateOf<FirebaseUser?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Log.d("auth", "${auth.currentUser}")
        auth.addAuthStateListener { auth ->
            currentUser = auth.currentUser
        }
        googleAuthSetup()
        val googleAuth = intent.getStringExtra("googleAuth")
        when (googleAuth){
            "login" -> googleLogIn()
            "logout" -> googleLogout()
        }
    }

    private fun googleAuthSetup() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        authResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val data: Intent? = result.data
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("auth", "firebaseAuthWithGoogle: ${account.id}")
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed
                    Log.d("auth", "firebaseAuthWithGoogle: failed ${e.message}")
                }
            }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    Log.d("auth", "firebaseAuthWithGoogle: successful")
                    currentUser = auth.currentUser
                    Toast.makeText(
                        this,
                        "${getString(R.string.signin_success)} ${currentUser?.displayName}",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                } else {
                    // Sign in fails
                    Log.d("auth", "firebaseAuthWithGoogle: failed ${task.exception}")
                    Toast.makeText(this, "${getString(R.string.signin_fail)}", Toast.LENGTH_LONG)
                        .show()
                }
            }
    }

    private fun googleLogIn() {
        val signInIntent = googleSignInClient.signInIntent
        authResultLauncher.launch(signInIntent)
    }

    private fun googleLogout() {
        auth.signOut()
        Toast.makeText(this, "${getString(R.string.action_logged_out)}", Toast.LENGTH_LONG).show()
        finish()
    }
}