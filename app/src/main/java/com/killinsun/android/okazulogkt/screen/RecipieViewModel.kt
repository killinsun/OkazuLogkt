package com.killinsun.android.okazulogkt.screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.killinsun.android.okazulogkt.data.Recipie
import com.killinsun.android.okazulogkt.data.repository.RecipieRepository
import kotlinx.coroutines.*

class RecipieViewModel: ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // Firebase Firestore
    val firestore: RecipieRepository =
        RecipieRepository()

    // recipies
    private var _recipies = MutableLiveData<MutableList<Recipie>?>()
    val recipies: LiveData<MutableList<Recipie>?>
        get() = _recipies

    init {
        initializeRecipies()
        Log.v("OkazuLog", "ViewModel RecipieViewModel initialized!")
    }

    private fun initializeRecipies() {
        uiScope.launch {
            val querySnapshot = firestore.fetchAllRecipies()
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
            if (newRecipie != null) {
                newRecipie.id = firestore.createNewRecipie(newRecipie)
                newRecipies.add(newRecipie)
                _recipies.postValue(newRecipies)
            }else{
                Log.v("OkazuLog" ,"newrecipie is null")
            }
        }
        return _recipies.value!!.size - 1
    }

    fun onUpdate(index:Int, newRecipie: Recipie?) {
        Log.v("OkazuLog", "newRecipie: ${newRecipie}")
        if (newRecipie != null) {
            _recipies.value?.set(index, newRecipie)
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