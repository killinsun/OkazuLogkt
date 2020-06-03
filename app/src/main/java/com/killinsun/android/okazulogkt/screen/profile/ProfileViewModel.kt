package com.killinsun.android.okazulogkt.screen.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.killinsun.android.okazulogkt.data.repository.RecipieRepository

class ProfileViewModel : ViewModel() {
    val firestore: RecipieRepository =
        RecipieRepository()

    val user = FirebaseAuth.getInstance().currentUser

    private lateinit var _uid: String
    val uid: String
        get() = _uid

    private var _gid: String
    val gid: String
        get() = _gid

    private var _displayName: String? = null
    val displayName: String?
        get() = _displayName

    private var _photoUrl: Uri? = null
    val photoUrl: Uri?
        get() = _photoUrl

    init {
        user?.let {
            _displayName = user.displayName
            _photoUrl = user.photoUrl
            _uid = user.uid
        }
        _gid = "hoge"

    }
}
