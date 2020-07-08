package com.killinsun.android.okazulogkt.data

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

data class Category(
    var id: String? = "",
    var gId: String? = "",
    var name: String? = ""
) {
    companion object {
        fun mapping(result: DocumentSnapshot): Category{
            return Category(
                result.id,
                result.get("gId") as String?,
                result.get("name") as String?
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
            "name" to this.name
        )
    }
}