package com.killinsun.android.okazulogkt.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.killinsun.android.okazulogkt.data.Category
import kotlinx.coroutines.tasks.await

class CategoryRepository {

    val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    suspend fun fetchAllCategories(gId: String): QuerySnapshot {
        val querySnapshot = db.collection("categories")
            .whereEqualTo("gId", gId)
            .get()
            .await()
        return querySnapshot
    }

    fun createNewCategory(category: Category, gId: String): String {
        val categoryRef = db.collection("categories").document()

        category.id = categoryRef.id
        category.gId = gId

        db.collection("categories")
            .document(categoryRef.id)
            .set(category.getByHashMap())
            .addOnSuccessListener {
                Log.v("OkazuLog", "category id: ${categoryRef.id} added successfully")
            }
            .addOnFailureListener {e ->
                Log.w("OkazuLog", "Error adding document", e)
            }

        return categoryRef.id
    }
}