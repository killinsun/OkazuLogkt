package com.killinsun.android.okazulogkt.screen.okazulog

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.killinsun.android.okazulogkt.databinding.OkazuLogFragmentBinding
import com.killinsun.android.okazulogkt.screen.RecipieViewModel
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

        sharedViewModel.user.observe(viewLifecycleOwner, Observer {
            sharedViewModel.initializeRecipies(sharedViewModel.user.value?.gId)
        })

        binding.viewmodel!!.recipies.observe(viewLifecycleOwner, Observer {
            val adapter = binding.okazuLogRv.adapter as OkazuLogAdapter?
            adapter?.setItem(it!!)
            Log.v("OkazuLog","Observe event!")
            Log.v("OkazuLog","args: ${sharedViewModel.recipies.value!!.size}")
        })
        this.binding = binding

        setHasOptionsMenu(true)
        return binding.root

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

        fab.setOnClickListener{ onClickFab(view) }
    }

    private fun onClickFab(view: View){
        view.findNavController().navigate(
            OkazuLogFragmentDirections.actionOkazuLogFragmentToRecipieEditor(-1)
        )
    }

}

