package com.killinsun.android.okazulogkt

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider


class GoogleAuthController(private val activity: AppCompatActivity) {
    val RC_SIGN_IN:Int = 2551

    private var completed: (FirebaseUser) -> (Unit) = {}

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    // GoogleSignInClient の生成
    private val googleSignInClient: GoogleSignInClient by lazy {
        GoogleSignIn.getClient(activity, googleSignInOptions)
    }

    // GoogleSignInClient のオプション、今回は特に設定が必要ないのでデフォルト値的なものを利用する
    private val googleSignInOptions: GoogleSignInOptions by lazy {
        val signInOption = GoogleSignInOptions.DEFAULT_SIGN_IN
        val idToken = activity.getString(R.string.default_web_client_id)
        GoogleSignInOptions.Builder(signInOption).requestIdToken(idToken).requestEmail().build()
    }

    fun startSignIn(completed: (FirebaseUser) -> (Unit)) {
        this.completed = completed
        val signInIntent = googleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        if (requestCode != RC_SIGN_IN) {
            Log.v("GoogleAuthController", "Result code was not matched. ${requestCode} ${RC_SIGN_IN}")
            return
        }

        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            Log.v("GoogleAuthController", "Google sign in success!")
            firebaseAuthWithGoogle(account!!)
        } catch(e: ApiException){
            Log.w("GoogleAuthController", "Google sign in failed", e)
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        Log.v("GoogleAuthController", "idToken " + account.idToken.toString())
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(activity) { task ->
            if(!task.isSuccessful){
                return@addOnCompleteListener
            }

            if(firebaseAuth.currentUser == null){
                return@addOnCompleteListener
            }

            completed(firebaseAuth.currentUser!!)
        }

    }

    fun startSignOut() {
        firebaseAuth.signOut()
        googleSignInClient.signOut()
    }
}