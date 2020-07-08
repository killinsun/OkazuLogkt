package com.killinsun.android.okazulogkt.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot

data class User(
    var id: String = "",
    var gId: String = "",
    var displayName: String? = "",
    var email: String? = "",
    var isAdmin: Boolean? = false
){
    companion object {
        fun mapping(result: DocumentSnapshot): User {
            var gId = ""
            if(result.get("gId") == "") {
               gId = result.get("gid")  as String
            }else{
                gId = result.get("gId") as String
            }
            return User(
                result.id,
                gId,
                result.get("displayName") as String?,
                result.get("email") as String?,
                result.get("isAdmin") as Boolean?
            )
        }
    }
    fun getByHashMap(): HashMap<String, Any?>{
        return hashMapOf(
            "userId" to this.id,
            "gId" to this.gId,
            "email" to this.email,
            "displayName" to this.displayName,
            "isAdmin" to this.isAdmin
        )
    }
}
