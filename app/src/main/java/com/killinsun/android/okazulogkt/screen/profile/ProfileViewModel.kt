package com.killinsun.android.okazulogkt.screen.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.killinsun.android.okazulogkt.data.Recipie
import com.killinsun.android.okazulogkt.data.User
import com.killinsun.android.okazulogkt.data.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    val userRepository = UserRepository()

    private val fbUser = FirebaseAuth.getInstance().currentUser

    private var _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private lateinit var _uid: String
    val uid: String
        get() = _uid

    private var _gid: String? = ""
    val gid: String?
        get() = _gid

    private var _displayName: String? = null
    val displayName: String?
        get() = _displayName

    private var _photoUrl: Uri? = null
    val photoUrl: Uri?
        get() = _photoUrl

    init {
        fbUser?.let {
            _displayName = fbUser.displayName
            _photoUrl = fbUser.photoUrl
            _uid = fbUser.uid
        }
        viewModelScope.launch {
            _user.value = userRepository.fetchUser(_uid)
            _gid = _user.value?.gId
        }

    }
}
