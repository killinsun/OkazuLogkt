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
        return if(docRef.data == null) {
            Log.v("OkazuLog", "user was null")
            createUser(userId)
        }else {
            User.mapping(docRef)
        }
    }

    suspend fun createUser(userId: String): User {
        val fbUser = FirebaseAuth.getInstance().currentUser
            ?: throw Error("You are not signed in now.")

        val groupData = hashMapOf(
            "name" to "test"
        )
        Log.v("OkazuLog", "Create a new user")
        val userRef = db.collection("users").document(userId)
        val groupRef = db.collection("groups").document()

        val user = User(
            id = fbUser.uid,
            gId = groupRef.id,
            displayName = fbUser.displayName,
            email = fbUser.email,
            isAdmin = false
        )

        groupRef.set(groupData).await()

        db.collection("users")
            .document(userRef.id)
            .set(user.getByHashMap())
            .addOnSuccessListener {
                Log.v("OkazuLog", "user id : ${userRef.id} added successfully")
            }
            .addOnFailureListener { e ->
                Log.w("OkazuLog", "Error adding document", e)
            }
            .await()

        return user
    }

}