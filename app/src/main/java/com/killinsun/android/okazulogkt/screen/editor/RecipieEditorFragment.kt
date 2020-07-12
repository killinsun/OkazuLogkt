package com.killinsun.android.okazulogkt.screen.editor

import android.content.Context
import android.os.Bundle
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
import androidx.lifecycle.ViewModelProviders
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

    private lateinit var viewModel: RecipieEditorViewModel
    private lateinit var recipieEditorVmFactory: RecipieEditorViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // -------------------------
        // ViewModel setup
        // -------------------------
        if(args.recipieIndex == -1){
            recipieEditorVmFactory = RecipieEditorViewModelFactory(
                null,
                sharedViewModel.user.value!!.gId
            )
        } else{
            recipieEditorVmFactory = RecipieEditorViewModelFactory(
                sharedViewModel.recipies.value?.get(args.recipieIndex),
                sharedViewModel.user.value!!.gId
            )
        }
        viewModel = ViewModelProviders.of(this, recipieEditorVmFactory)
            .get(RecipieEditorViewModel::class.java)


        // -------------------------
        // Data Binding setup
        // -------------------------
        binding = RecipieEditorFragmentBinding.inflate(inflater, container, false).apply{
            viewmodel = viewModel
            recipieIndex = args.recipieIndex
            lifecycleOwner = viewLifecycleOwner
        }

        binding.viewmodel!!.categories.observe(viewLifecycleOwner, Observer{
            if(it != null) {
                val categoryAdapter: CategoryAdapter = CategoryAdapter(context, it)
                binding.categorySpinner.adapter = categoryAdapter
                viewModel.setCategoryPosition()
                binding.categorySpinner.setSelection(viewModel.categorySpinnerPosition)
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
        binding.viewmodel!!.createNewCategory()
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