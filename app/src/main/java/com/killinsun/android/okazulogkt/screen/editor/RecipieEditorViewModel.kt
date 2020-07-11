package com.killinsun.android.okazulogkt.screen.editor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.killinsun.android.okazulogkt.data.Category
import com.killinsun.android.okazulogkt.data.Recipie
import com.killinsun.android.okazulogkt.data.repository.CategoryRepository
import kotlinx.coroutines.launch
import java.util.*

class RecipieEditorViewModel : ViewModel() {

    private val categoryRepository = CategoryRepository()

    private var _categories = MutableLiveData<MutableList<Category>?>()
    val categories: LiveData<MutableList<Category>?>
        get() = _categories

    private var _category = Category()
    val category: Category
            get() = _category

    private var _edittingRecipie = MutableLiveData<Recipie>()
    val edittingRecipie: LiveData<Recipie>
        get() = _edittingRecipie

    init {
        _edittingRecipie.value = Recipie(count = 1)
    }

    fun setRecipie(recipie: Recipie) {
        _edittingRecipie.value = recipie
        _category.id = recipie.categoryId
    }

    fun fetchCategories(gId: String?) {
        // TODO: danger code
        if(gId == null) return

        viewModelScope.launch {
            val querySnapshot = categoryRepository.fetchAllCategories(gId)
            _categories.postValue(Category.mapping(querySnapshot))
        }
    }

    fun createNewCategory(gId: String?) {
        // TODO: danger code
        if(gId == null) return

        categoryRepository.createNewCategory(_category, gId)
        _categories.value?.add(_category)
        _category = Category()
    }

    fun onCalendarSelected(year: Int, month: Int, dayOfMonth: Int){
        val c: Calendar = Calendar.getInstance()
        c.set(year, month, dayOfMonth)

//        _edittingRecipie.value?.lastDate = c.time
        _edittingRecipie.postValue(_edittingRecipie.value?.copy(lastDate = c.time))
    }
}