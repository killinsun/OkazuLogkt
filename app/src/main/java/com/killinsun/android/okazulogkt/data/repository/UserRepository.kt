package com.killinsun.android.okazulogkt.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.killinsun.android.okazulogkt.data.Recipie
import com.killinsun.android.okazulogkt.data.User
import kotlinx.coroutines.tasks.await

class UserRepository {

    val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    suspend fun fetchUser(userId: String): User {
        val docRef = db.collection("users")
            .document(userId)
            .get()
            .await()
        Log.v("OkazuLog", "fetch complete")
        return User.mapping(docRef)
    }

}