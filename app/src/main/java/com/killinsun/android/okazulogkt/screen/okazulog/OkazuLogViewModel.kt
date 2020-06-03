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

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // Firebase Firestore
    val firestore: RecipieRepository =
        RecipieRepository()

    // Recipies
    private var _recipies = MutableLiveData<List<Recipie>?>()
    val recipies:LiveData<List<Recipie>?>
        get() = _recipies

    // User Email
    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String>
        get() = _userEmail

    // User
    private val _user = MutableLiveData<FirebaseUser>()
    val user: LiveData<FirebaseUser>
        get() = _user

    // Recipie Name array
    private val _recipieNameArray = MutableLiveData<ArrayList<String>>()
    val recipieNameArray: LiveData<ArrayList<String>>
        get() = _recipieNameArray


    init{
        Log.v("OkazuLogViewModel", "ViewModel created!")
        _user.value = FirebaseAuth.getInstance().currentUser
        _userEmail.value = user.value?.email
        initializeRecipies()
    }

    private fun initializeRecipies() {
        uiScope.launch {
            val querySnapshot = firestore.fetchAllRecipies()
            val recipieNames = arrayListOf<String>()

            for(document in querySnapshot){
                recipieNames.add(document.data["name"].toString())
            }
            _recipieNameArray.value = recipieNames
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
