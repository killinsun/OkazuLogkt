package com.killinsun.android.okazulogkt.screen.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.killinsun.android.okazulogkt.databinding.RecipieDetailFragmentBinding
import com.killinsun.android.okazulogkt.screen.RecipieViewModel
import io.wovn.wovnkt.Lang
import io.wovn.wovnkt.Wovn

class RecipieDetailFragment : Fragment() {

    private lateinit var binding: RecipieDetailFragmentBinding

    private val args: RecipieDetailFragmentArgs by navArgs()
    private val sharedViewModel: RecipieViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecipieDetailFragmentBinding.inflate(inflater, container, false)
        binding.viewmodel = sharedViewModel
        binding.recipieIndex = args.recipieIndex

        Log.v("OkazuLog", sharedViewModel.recipies.value?.get(args.recipieIndex).toString())
        binding.editBtn.setOnClickListener{ onClickEditButton() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = sharedViewModel.recipies.value?.get(args.recipieIndex)?.name

        Log.v("OkazuLog", "${Wovn.getCurrentLang()}")
        Wovn.changeLang(Lang.japanese)
        view?.let { Wovn.translateView(it, "main") }
    }

    private fun onClickEditButton(){
        findNavController().navigate(
            RecipieDetailFragmentDirections.actionRecipieDetailFragmentToRecipieEditor(args.recipieIndex)
        )
    }
}

