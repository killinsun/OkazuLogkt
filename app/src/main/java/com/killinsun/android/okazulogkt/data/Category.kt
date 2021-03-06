package com.killinsun.android.okazulogkt.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.util.*
import kotlin.collections.HashMap

data class Category(
    var id: String? = "",
    var gId: String? = "",
    var name: String? = "",
    var timestamp: Date = Date()
) {
    companion object {
        fun mapping(result: DocumentSnapshot): Category{
            val timestamp: Timestamp = result.get("timestamp") as Timestamp
            return Category(
                result.id,
                result.get("gId") as String?,
                result.get("name") as String?,
                timestamp.toDate()
            )
        }
        fun mapping(result: QuerySnapshot): MutableList<Category> {
            val categories = mutableListOf<Category>()
            result.forEach {
                categories.add(Category.mapping(it))
            }
            return categories
        }
    }

    fun getByHashMap(): HashMap<String, Any?>{
        return hashMapOf(
            "id" to this.id,
            "gId" to this.gId,
            "name" to this.name,
            "timestamp" to this.timestamp
        )
    }

    fun getPositionById(categories: MutableList<Category>, id: String): Int{
        val defaultPosition = 0

        var i = 0
        run loop@ {
            categories.forEach{
                if(it.id == id) return@loop
                i++
            }

        }
        if(i > categories.size - 1) return defaultPosition

        return i
    }
}