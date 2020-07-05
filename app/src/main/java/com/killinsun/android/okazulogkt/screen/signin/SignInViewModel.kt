package com.killinsun.android.okazulogkt.screen.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.killinsun.android.okazulogkt.data.User
import com.killinsun.android.okazulogkt.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SignInViewModel: ViewModel() {
    private var viewModelJob = Job()

    val userRepository: UserRepository = UserRepository()

    // TODO: MutableLiveDataにする必要あるか検討
    private val _fbUser = MutableLiveData<FirebaseUser>()

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    var isLogin: Boolean = false

    init{
        _fbUser.value = FirebaseAuth.getInstance().currentUser
        if(_fbUser.value?.uid != null) {
            isLogin = true
        }
    }

    fun onLogin() {
        _fbUser.value = FirebaseAuth.getInstance().currentUser
        viewModelScope.launch {
            _user.value = _fbUser.value?.uid?.let {
                userRepository.fetchUser(it)
            }
        }

    }

}