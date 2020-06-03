package com.killinsun.android.okazulogkt.screen.signin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignInViewModel: ViewModel() {

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String>
        get() = _userEmail

    private val _user = MutableLiveData<FirebaseUser>()
    val user: LiveData<FirebaseUser>
        get() = _user
//    val user = FirebaseAuth.getInstance().currentUser

    var isLogin: Boolean = false

    init{
        _user.value = FirebaseAuth.getInstance().currentUser
        _userEmail.value = user.value?.email
        Log.v("SignInViewModel", "ViewModel created!")
        Log.v("SignInViewModel", "current user " + userEmail.value)
        if(_user.value?.uid != null) {
            isLogin = true
        }

    }

    fun onLogin() {
        _user.value = FirebaseAuth.getInstance().currentUser
    }

}