package com.killinsun.android.okazulogkt.screen.editor

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.killinsun.android.okazulogkt.data.Category
import com.killinsun.android.okazulogkt.data.Recipie
import com.killinsun.android.okazulogkt.data.repository.CategoryRepository
import kotlinx.coroutines.launch
import java.util.*

class RecipieEditorViewModel(val recipie: Recipie?, val gId: String) : ViewModel() {

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

    private var _categorySpinnerPosition: Int = 0 // default position is 0
    val categorySpinnerPosition: Int
        get() = _categorySpinnerPosition


    init {
        _edittingRecipie.value = Recipie(count = 1)
        Log.v("OkazuLog", "viewmodel initialize ${recipie}")
        setRecipie()
        fetchCategories()
    }

    fun setRecipie() {
        if(this.recipie == null) return

        _edittingRecipie.value = this.recipie
        _category.id = this.recipie.categoryId

    }

    fun fetchCategories() {
        viewModelScope.launch {
            val querySnapshot = categoryRepository.fetchAllCategories(gId)
            _categories.postValue(Category.mapping(querySnapshot))
            setCategoryPosition()
        }
    }

    fun setCategoryPosition(){
        val categories= _categories.value
        val categoryId = _category.id
        if(categories == null || categoryId == null) return

        _categorySpinnerPosition = _category.getPositionById(categories, categoryId)
    }

    fun createNewCategory(){
        categoryRepository.createNewCategory(_category, this.gId)
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