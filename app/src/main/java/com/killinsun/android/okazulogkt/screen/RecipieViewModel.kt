package com.killinsun.android.okazulogkt.screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.killinsun.android.okazulogkt.data.Recipie
import com.killinsun.android.okazulogkt.data.repository.RecipieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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

    fun onUpdate(index:Int, newRecipie: Recipie?) {
        Log.v("OkazuLog", "newRecipie: ${newRecipie}")
        if (newRecipie != null) {
            _recipies.value?.set(index, newRecipie)
        }
    }

}