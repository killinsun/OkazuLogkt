package com.killinsun.android.okazulogkt.screen.editor

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.killinsun.android.okazulogkt.R
import com.killinsun.android.okazulogkt.databinding.RecipieEditorFragmentBinding
import com.killinsun.android.okazulogkt.screen.RecipieViewModel

class RecipieEditorFragment :
    Fragment(),
    DatePick.OnDateSelectedListener,
    CatAddDialogFragment.OnClickAddButtonListener{

    private lateinit var binding: RecipieEditorFragmentBinding

    private val args: RecipieEditorFragmentArgs by navArgs()
    private val sharedViewModel: RecipieViewModel by activityViewModels()
    private val viewModel: RecipieEditorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecipieEditorFragmentBinding.inflate(inflater, container, false).apply{
            viewmodel = viewModel
            recipieIndex = args.recipieIndex
            lifecycleOwner = viewLifecycleOwner
        }

        binding.viewmodel!!.fetchCategories(sharedViewModel.user.value?.gId)

        binding.viewmodel!!.categories.observe(viewLifecycleOwner, Observer{
            if(it != null) {
                val categoryAdapter: CategoryAdapter = CategoryAdapter(context, it)
                binding.categorySpinner.adapter = categoryAdapter

                Log.v("OkazuLog", "vm category id : ${viewModel.category.id}")
                val position = viewModel.category.getPositionById(viewModel.categories.value, viewModel.category.id)
                Log.v("OkazuLog", "category position is $position")
                binding.categorySpinner.setSelection(position)
            }
        })

        binding.categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(_p0: AdapterView<*>?, _p1: View?, position: Int, _p3: Long) {
                viewModel.let {
                    it.edittingRecipie.value?.categoryId = it.categories.value?.get(position)?.id
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.addBtn.setOnClickListener{ onClickAddButton() }
        binding.addCategoryBtn.setOnClickListener{ onClickCatAddButton() }
        binding.updateBtn.setOnClickListener{ onClickUpdateButton() }
        binding.showDatePickerBtn.setOnClickListener { showDatePickerDialog() }


        assignRecipieToBinding()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(args.recipieIndex != -1) {
            (activity as AppCompatActivity).supportActionBar?.title =
                sharedViewModel.recipies.value?.get(args.recipieIndex)?.name
        } else {
            (activity as AppCompatActivity).supportActionBar?.title = "新しいおかずを追加"

        }

        assignRecipieToBinding()

    }

    private fun assignRecipieToBinding(){
       if(args.recipieIndex != -1) {
           sharedViewModel.recipies.value?.get(args.recipieIndex)?.let { viewModel.setRecipie(it.copy()) }
       }
    }

    private fun onClickAddButton() {
        val index = sharedViewModel.onCreate(binding.viewmodel!!.edittingRecipie.value)

        hideKeyboard()

        findNavController().navigate(
            RecipieEditorFragmentDirections.actionRecipieEditorToRecipieDetailFragment(index)
        )
    }

    private fun onClickUpdateButton() {
        sharedViewModel.onUpdate(args.recipieIndex, binding.viewmodel!!.edittingRecipie.value)

        hideKeyboard()

        findNavController().navigate(
            RecipieEditorFragmentDirections.actionRecipieEditorToRecipieDetailFragment(args.recipieIndex)
        )
    }

    private fun onClickCatAddButton() {
        val dialogFragment = CatAddDialogFragment(
            getString(R.string.addCategory),
            getString(R.string.dscCategory),
            getString(R.string.add),
            getString(R.string.cancel)
        )
        childFragmentManager?.run {
            dialogFragment.show(this, "CategoryAddDialog")
        }

    }

    override fun onClickDialogPositiveButton(categoryName: String) {
        binding.viewmodel!!.category.name = categoryName
        binding.viewmodel!!.createNewCategory(sharedViewModel.user.value?.gId)
    }

    private fun showDatePickerDialog() {
        val datePick = DatePick()
        datePick.show(childFragmentManager, "日付")
    }

    override fun onSelected(year: Int, month: Int, dayOfMonth: Int){
        binding.viewmodel!!.onCalendarSelected(year, month, dayOfMonth )
    }

    private fun hideKeyboard() {
        val view = activity?.currentFocus
        if(view != null){
            val manager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}