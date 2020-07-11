package com.killinsun.android.okazulogkt.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.killinsun.android.okazulogkt.data.Recipie
import kotlinx.coroutines.tasks.await

class RecipieRepository {

    private val TAG = "FireStore"
    var recipies: List<Recipie>? = null

    val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }


    suspend fun fetchAllRecipies(gId: String): QuerySnapshot {
        val querySnapshot = db.collection("recipies")
            .whereEqualTo("gId", gId)
            .orderBy("lastDate", Query.Direction.ASCENDING)
            .get()
            .await()
        return querySnapshot
    }

    fun createNewRecipie(recipie: Recipie, gId: String): String{
       val recipieRef = db.collection("recipies").document()

        recipie.id = recipieRef.id
        recipie.gId = gId
        db.collection("recipies")
            .document(recipieRef.id)
            .set(recipie.getByHashMap())
            .addOnSuccessListener {
                Log.v("OkazuLog", "recipie id: ${recipieRef.id} added succcessfully.")
            }
            .addOnFailureListener{ e ->
                Log.w("OkazuLog", "Error adding document", e)
            }

        return recipieRef.id
    }

    fun updateRecipie(recipie: Recipie) {
        db.collection("recipies").document(recipie.id)
            .set(recipie.getByHashMap(), SetOptions.merge())
            .addOnSuccessListener {
                Log.v("OkazuLog", "recipie id: ${recipie.id} updated succcessfully.")
            }
            .addOnFailureListener{ e ->
                Log.w("OkazuLog", "Error updating document", e)
            }

    }

    suspend fun deleteRecipie(recipieId: String) {
        db.collection("recipies")
            .document(recipieId)
            .delete()
            .addOnSuccessListener { Log.v("OkazuLog", "recipie id: ${recipieId} delete successfully.") }
            .addOnFailureListener{ e->  Log.w("OkazuLog", "Error deleting document", e)}
            .await()
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