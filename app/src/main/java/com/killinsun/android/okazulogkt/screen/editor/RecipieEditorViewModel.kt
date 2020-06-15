package com.killinsun.android.okazulogkt.screen.editor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.killinsun.android.okazulogkt.data.Recipie
import java.util.*

class RecipieEditorViewModel : ViewModel() {

    private var _edittingRecipie = MutableLiveData<Recipie>()
    val edittingRecipie: LiveData<Recipie>
        get() = _edittingRecipie

    fun setRecipie(recipie: Recipie) {
        _edittingRecipie.value = recipie
    }

    fun onCalendarSelected(year: Int, month: Int, dayOfMonth: Int){
        val c: Calendar = Calendar.getInstance()
        c.set(year, month, dayOfMonth)

//        _edittingRecipie.value?.lastDate = c.time
        _edittingRecipie.postValue(_edittingRecipie.value?.copy(lastDate = c.time))
    }
}