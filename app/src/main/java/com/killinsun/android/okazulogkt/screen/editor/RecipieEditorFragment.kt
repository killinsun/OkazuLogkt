package com.killinsun.android.okazulogkt.screen.editor

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.killinsun.android.okazulogkt.data.Recipie
import com.killinsun.android.okazulogkt.databinding.RecipieEditorFragmentBinding
import com.killinsun.android.okazulogkt.screen.RecipieViewModel
import io.wovn.wovnkt.Lang
import io.wovn.wovnkt.Wovn

class RecipieEditorFragment : Fragment(), DatePick.OnDateSelectedListener{

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

        binding.addBtn.setOnClickListener{ onClickAddButton()}
        binding.updateBtn.setOnClickListener{ onClickUpdateButton()}
        binding.showDatePickerBtn.setOnClickListener { showDatePickerDialog()}

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

        Log.v("OkazuLog", "${Wovn.getCurrentLang()}")
        Wovn.changeLang(Lang.english)
        view?.let { Wovn.translateView(it, "main") }

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