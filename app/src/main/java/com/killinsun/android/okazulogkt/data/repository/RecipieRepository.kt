package com.killinsun.android.okazulogkt.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.killinsun.android.okazulogkt.data.Recipie
import kotlinx.coroutines.tasks.await

class RecipieRepository {

    private val TAG = "FireStore"
    var recipies: List<Recipie>? = null

    val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    suspend fun fetchAllRecipies(): QuerySnapshot {
        val querySnapshot = db.collection("recipies")
            .whereEqualTo("gId", "IDdexpFiBuPCWV5RexAD")
            .orderBy("lastDate", Query.Direction.ASCENDING)
            .get()
            .await()
        return querySnapshot
    }

//    suspend fun fetchAllRecipies(): List<Recipie>? {
//            db.collection("recipies")
//            .whereEqualTo("gId", "IDdexpFiBuPCWV5RexAD")
//            .get()
//            .addOnSuccessListener { documents ->
//                Log.v("Coroutine","coroutine")
//                recipies = documents.toObjects(Recipie::class.java)
//            }
//            .addOnFailureListener{ exception ->
//                Log.e(TAG, "Error getting documents: ", exception)
//            }
//            .await()
//            return recipies
//        }
//        db.collection("recipies")
//            .whereEqualTo("gId", "IDdexpFiBuPCWV5RexAD")
//            .get()
//            .addOnSuccessListener { documents ->
//                recipies = documents.toObjects(Recipie::class.java)
//            }
//            .addOnFailureListener{ exception ->
//                Log.e(TAG, "Error getting documents: ", exception)
//            }
//        return recipies
//    }

}