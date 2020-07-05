package com.killinsun.android.okazulogkt.screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.killinsun.android.okazulogkt.data.Recipie
import com.killinsun.android.okazulogkt.data.User
import com.killinsun.android.okazulogkt.data.repository.RecipieRepository
import com.killinsun.android.okazulogkt.data.repository.UserRepository
import kotlinx.coroutines.*

class RecipieViewModel: ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // Firebase Firestore
    val firestore: RecipieRepository =
        RecipieRepository()

    val userRepository: UserRepository =
        UserRepository()

    // recipies
    private var _recipies = MutableLiveData<MutableList<Recipie>?>()
    val recipies: LiveData<MutableList<Recipie>?>
        get() = _recipies

    private val fbUser = FirebaseAuth.getInstance().currentUser

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user


    init {
        viewModelScope.launch {
            if (fbUser != null) {
                _user.value = userRepository.fetchUser(fbUser.uid)
            }
        }
    }

    fun initializeRecipies(gId: String?) {
        if(gId == null) return
        viewModelScope.launch {
            val querySnapshot = firestore.fetchAllRecipies(gId)
            _recipies.postValue(Recipie.mapping(querySnapshot))
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onCreate(newRecipie: Recipie?): Int {
        val newRecipies:MutableList<Recipie> = _recipies.value ?: arrayListOf()
        runBlocking {
            Log.v("OkazuLog", "${newRecipies.size}")
            if (newRecipie != null) {
                newRecipie.id = firestore.createNewRecipie(newRecipie, _user.value!!.gId)
                newRecipies.add(newRecipie)
                _recipies.postValue(newRecipies)
            }else{
                Log.v("OkazuLog" ,"newrecipie is null")
            }
        }
        return _recipies.value!!.size - 1
    }

    fun onUpdate(index:Int, newRecipie: Recipie?) {
        runBlocking {
            if (newRecipie != null) {
                firestore.updateRecipie(newRecipie)
                _recipies.value?.set(index, newRecipie)
            }
        }
    }

    fun onDelete(index: Int){
        val deleteRecipie = _recipies.value?.get(index)
        val newRecipies:MutableList<Recipie> = _recipies.value ?: arrayListOf()
        uiScope.launch {
            if (deleteRecipie != null) {
                firestore.deleteRecipie(deleteRecipie.id)
            }
            newRecipies.remove(deleteRecipie)
        }
        _recipies.value?.remove(deleteRecipie)
    }

}