package com.killinsun.android.okazulogkt.screen.okazulog

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager

import com.killinsun.android.okazulogkt.R
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
                view!!.findNavController())
                || super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.okazuLogRv.layoutManager = LinearLayoutManager(this.context)

        val adapter = OkazuLogAdapter(binding.viewmodel!!, this)
        binding.okazuLogRv.adapter = adapter

        adapter.setOnItemClickListener(object: OkazuLogAdapter.OnItemClickListener {
            override fun onClick(view: View, position: Int){
                Log.v("OkazuLog", "${viewModel.recipies.value?.get(position)}")
                Toast.makeText(context, "tap event! ${viewModel.recipies.value?.get(position)}", Toast.LENGTH_LONG).show()
            }
        })

    }

}

