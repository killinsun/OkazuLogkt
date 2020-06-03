package com.killinsun.android.okazulogkt.screen.okazulog

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI

import com.killinsun.android.okazulogkt.R
import com.killinsun.android.okazulogkt.databinding.ActivitySignInBinding
import com.killinsun.android.okazulogkt.databinding.OkazuLogFragmentBinding
import kotlinx.android.synthetic.main.okazu_log_fragment.*

class OkazuLogFragment : Fragment() {

    private lateinit var binding: OkazuLogFragmentBinding
    private lateinit var viewModel: OkazuLogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(OkazuLogViewModel::class.java)

        binding = OkazuLogFragmentBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)
        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!,
                view!!.findNavController())
                || super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter<String>(context!! , android.R.layout.simple_list_item_1)
        binding.testListView.adapter = adapter
        viewModel.recipieNameArray.observe(this, Observer { name ->
            adapter.clear()
            name?.let { adapter.addAll(it)}
        })
    }
}
