package com.killinsun.android.okazulogkt.screen.okazulog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.killinsun.android.okazulogkt.data.Recipie
import com.killinsun.android.okazulogkt.data.repository.RecipieRepository
import kotlinx.coroutines.*

class OkazuLogViewModel : ViewModel() {

    //
    // NOTICE: Recipie class objects are stored at RecipieViewModel
    //

    // Firebase Firestore
    val firestore: RecipieRepository =
        RecipieRepository()

    // User
    private val _user = MutableLiveData<FirebaseUser>()
    val user: LiveData<FirebaseUser>
        get() = _user

    // User Email
    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String>
        get() = _userEmail

    init{
        Log.v("OkazuLogViewModel", "ViewModel created!")
        _user.value = FirebaseAuth.getInstance().currentUser
        _userEmail.value = user.value?.email
    }

    override fun onCleared() {
        super.onCleared()
    }
}
