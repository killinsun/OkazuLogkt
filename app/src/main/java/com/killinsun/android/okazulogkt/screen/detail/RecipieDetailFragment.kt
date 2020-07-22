package com.killinsun.android.okazulogkt.screen.detail

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.killinsun.android.okazulogkt.databinding.RecipieDetailFragmentBinding
import com.killinsun.android.okazulogkt.screen.RecipieViewModel
import com.killinsun.android.okazulogkt.screen.editor.CategoryAdapter
import io.wovn.wovnkt.Lang
import io.wovn.wovnkt.Wovn
import io.wovn.wovnkt.extensions.ignore.wovnIgnore
import kotlinx.android.synthetic.main.recipie_detail_fragment.*

class RecipieDetailFragment : Fragment() {

    private lateinit var binding: RecipieDetailFragmentBinding

    private val args: RecipieDetailFragmentArgs by navArgs()
    private val sharedViewModel: RecipieViewModel by activityViewModels()
    private val viewModel: RecipieDetailViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecipieDetailFragmentBinding.inflate(inflater, container, false)
        binding.viewmodel = sharedViewModel
        binding.recipieIndex = args.recipieIndex

        viewModel.fetchCategory(sharedViewModel.recipies.value?.get(args.recipieIndex)?.categoryId)

        viewModel.category.observe(viewLifecycleOwner, Observer {
            binding.categoryName = it.name
        })

        binding.editBtn.setOnClickListener{ onClickEditButton() }
        binding.deleteBtn.setOnClickListener { onClickDeleteButton()  }

        binding.recipieDetailTv.movementMethod = ScrollingMovementMethod()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        (activity as AppCompatActivity).supportActionBar?.title = sharedViewModel.recipies.value?.get(args.recipieIndex)?.name

        val sharedPreferences:SharedPreferences = requireActivity().getSharedPreferences("Preferences", Context.MODE_PRIVATE)
        Log.v("OkazuLog", sharedPreferences.getString("WOVN_LANG", ""))

        Wovn.changeLang(Lang.english)
        Wovn.translateView(textView8, "detail")
        Wovn.translateView(textView4, "detail")
        Wovn.translateView(textView6, "detail")
        Wovn.translateView(textView7, "detail")
        Wovn.translateView(editBtn, "detail")
        Wovn.translateView(deleteBtn, "detail")

        deleteBtn.setBackgroundColor(Color.RED)
        editBtn.setBackgroundColor(Color.GREEN)

    }

    private fun onClickEditButton(){
        findNavController().navigate(
            RecipieDetailFragmentDirections.actionRecipieDetailFragmentToRecipieEditor(args.recipieIndex)
        )
    }

    private fun onClickDeleteButton() {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle("削除")
                .setMessage("本当に削除しますか？この操作は取り消せません")
                .setPositiveButton("削除") { dialog, which ->
                    sharedViewModel.onDelete(args.recipieIndex)
                    findNavController().popBackStack()
                }
                .setNegativeButton("取り消し", null)
                .show()
        }
    }
}

