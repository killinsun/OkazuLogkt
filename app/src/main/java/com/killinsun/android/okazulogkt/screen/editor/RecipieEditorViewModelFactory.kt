package com.killinsun.android.okazulogkt.screen.editor

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.killinsun.android.okazulogkt.data.Recipie

class RecipieEditorViewModelFactory(
    private val recipie: Recipie?,
    private val gId: String
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RecipieEditorViewModel::class.java)){
            return RecipieEditorViewModel(recipie, gId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    fun getViewModelFactory(): RecipieEditorViewModelFactory {
        return RecipieEditorViewModelFactory(recipie, gId)
    }
}