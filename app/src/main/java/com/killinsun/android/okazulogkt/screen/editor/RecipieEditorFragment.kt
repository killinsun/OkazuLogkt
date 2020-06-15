package com.killinsun.android.okazulogkt.screen.editor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.killinsun.android.okazulogkt.data.Recipie
import com.killinsun.android.okazulogkt.databinding.RecipieEditorFragmentBinding
import com.killinsun.android.okazulogkt.screen.RecipieViewModel

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

        binding.updateBtn.setOnClickListener{ onClickUpdateButton()}
        binding.showDatePickerBtn.setOnClickListener { showDatePickerDialog()}

        assignRecipieToBinding()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = sharedViewModel.recipies.value?.get(args.recipieIndex)?.name

        assignRecipieToBinding()
    }

    private fun assignRecipieToBinding(){
       if(args.recipieIndex != -1) {
           sharedViewModel.recipies.value?.get(args.recipieIndex)?.let { viewModel.setRecipie(it) }
       }
    }

    private fun onClickUpdateButton() {
         // TODO: 逆方向のbindingがうまくいかないので各入力項目の.textの値を取得するように変更する
        sharedViewModel.onUpdate(args.recipieIndex, binding.viewmodel!!.edittingRecipie.value)

        //TODO: キーボード非表示にする

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

}