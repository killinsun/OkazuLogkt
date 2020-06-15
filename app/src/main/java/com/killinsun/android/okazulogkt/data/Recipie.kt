package com.killinsun.android.okazulogkt.data

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.sql.Time
import java.util.*

data class Recipie (
    val id: String = "",
    var name: String = "",
    var lastDate: Date = Date(),
    var count: Long = 0,
    var favorited: Boolean = false,
    var imageSrc: String = "/noimage.jpg"
) {
    companion object {
        fun mapping(result: DocumentSnapshot): Recipie {
            val lastDateTs: Timestamp = result.get("lastDate") as Timestamp
            return Recipie(
                result.id,
                result.get("name") as String,
                lastDateTs.toDate(),
                result.get("count") as Long,
                result.get("favorited") as Boolean,
                result.get("imageSrc") as String
            )
        }
        fun mapping(result: QuerySnapshot): MutableList<Recipie> {
            val recipies = mutableListOf<Recipie>()
            result.forEach {
                recipies.add(mapping(it))
            }
            return recipies
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

