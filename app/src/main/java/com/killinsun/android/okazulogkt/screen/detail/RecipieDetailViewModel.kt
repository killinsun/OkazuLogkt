package com.killinsun.android.okazulogkt.screen.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.killinsun.android.okazulogkt.data.Category
import com.killinsun.android.okazulogkt.data.Recipie
import com.killinsun.android.okazulogkt.data.repository.CategoryRepository
import kotlinx.coroutines.launch

class RecipieDetailViewModel : ViewModel() {

    private val categoryRepository = CategoryRepository()

    private var _categories = MutableLiveData<MutableList<Category>?>()

    private var _category = MutableLiveData<Category>()
    val category: LiveData<Category>
        get() = _category

    fun fetchCategory(id: String?) {
        if(id == null) return

        viewModelScope.launch {
            val querySnapshot = categoryRepository.fetchCategory(id)
            _category.postValue(Category.mapping(querySnapshot))
        }
    }
}