package com.killinsun.android.okazulogkt.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot

data class User(
    var id: String = "",
    var gId: String = "",
    var displayName: String = "",
    var email: String = "",
    var isAdmin: Boolean = false
){
    companion object {
        fun mapping(result: DocumentSnapshot): User {
            return User(
                result.id,
                result.get("gId") as String,
                result.get("displayName") as String,
                result.get("email") as String
            )
        }
    }
}
