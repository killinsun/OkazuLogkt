package com.killinsun.android.okazulogkt.screen.okazulog

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager

import com.killinsun.android.okazulogkt.R
import com.killinsun.android.okazulogkt.databinding.OkazuLogFragmentBinding
import com.killinsun.android.okazulogkt.screen.RecipieViewModel
import io.wovn.wovnkt.Lang
import io.wovn.wovnkt.Wovn
import kotlinx.android.synthetic.main.okazu_log_fragment.*

class OkazuLogFragment : Fragment() {

    private lateinit var binding: OkazuLogFragmentBinding

    private val sharedViewModel: RecipieViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = OkazuLogFragmentBinding.inflate(inflater, container, false)
        binding.viewmodel = sharedViewModel

        binding.viewmodel!!.recipies.observe(viewLifecycleOwner, Observer {
            val adapter = binding.okazuLogRv.adapter as OkazuLogAdapter?
            adapter?.setItem(it!!)
            Log.v("OkazuLog","Observe event!")
        })
        this.binding = binding

        setHasOptionsMenu(true)
        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
                requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title ="OkazuLog"

        binding.okazuLogRv.layoutManager = LinearLayoutManager(this.context)

        val adapter = OkazuLogAdapter(binding.viewmodel!!, this)
        binding.okazuLogRv.adapter = adapter

        adapter.setOnItemClickListener(object: OkazuLogAdapter.OnItemClickListener {
            override fun onClick(view: View, position: Int){
                view.findNavController().navigate(
                    OkazuLogFragmentDirections.actionOkazuLogFragmentToRecipieDetailFragment(position)
                )
            }
        })
        fab.setOnClickListener{
           Toast.makeText(activity, "Add tapped", Toast.LENGTH_SHORT) .show()
        }

        Log.v("OkazuLog", "${Wovn.getCurrentLang()}")
        Wovn.changeLang(Lang.english)
        view?.let { Wovn.translateView(it, "main") }
    }

}

