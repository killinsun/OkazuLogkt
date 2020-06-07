package com.killinsun.android.okazulogkt.data

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.util.*

data class Recipie (
    val id: String = "",
    val name: String = "",
    val lastDate: Date = Date(),
    val count: Long = 0,
    val favorited: Boolean = false,
    val imageSrc: String = "/noimage.jpg"
) {
    companion object {
        fun mapping(result: DocumentSnapshot): Recipie {
            return Recipie(
                result.id,
                result.get("name") as String,
                Date(),
                result.get("count") as Long,
                result.get("favorited") as Boolean,
                result.get("imageSrc") as String
            )
        }
        fun mapping(result: QuerySnapshot): List<Recipie> {
            val recipies = mutableListOf<Recipie>()
            result.forEach {
                recipies.add(mapping(it))
            }
            return recipies.toList()
        }
    }

    override fun toString(): String {
        return "ID: ${this.id}, " +
                "Name: ${this.name}, " +
                "lastDate: ${this.lastDate}," +
                "CookCount: ${this.count}," +
                "Favorited: ${this.favorited}," +
                "ImageSrc: ${this.imageSrc} "
    }
}

