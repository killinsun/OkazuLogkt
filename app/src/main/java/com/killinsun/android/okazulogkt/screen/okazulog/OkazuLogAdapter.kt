package com.killinsun.android.okazulogkt.screen.okazulog

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.killinsun.android.okazulogkt.R
import com.killinsun.android.okazulogkt.data.Recipie
import com.killinsun.android.okazulogkt.databinding.OkazuLogRvItemBinding
import com.killinsun.android.okazulogkt.screen.RecipieViewModel

class OkazuLogAdapter (
    private val viewModel: RecipieViewModel,
    private val parentLifecycleOwner: LifecycleOwner
    ) : RecyclerView.Adapter<OkazuLogAdapter.OkazuLogViewHolder>(){

    private var recipies = listOf<Recipie>()
    lateinit var listener: OnItemClickListener

    override fun getItemCount(): Int {
        return recipies.size
    }

    fun setItem(recipies: List<Recipie>){
        this.recipies = recipies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OkazuLogViewHolder {

        val binding = DataBindingUtil.inflate<OkazuLogRvItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.okazu_log_rv_item,
            parent,
            false
        )
        return OkazuLogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OkazuLogViewHolder, position: Int) {

        holder.binding.viewmodel = viewModel
        holder.binding.position = position
        Log.v("OkazuLog", "${position} :" + viewModel.recipies.value?.get(position).toString())
        holder.binding.rvItem.setOnClickListener {
            listener.onClick(it, position)
        }
        holder.binding.lifecycleOwner = parentLifecycleOwner
    }

    interface OnItemClickListener {
        fun onClick(view: View, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class OkazuLogViewHolder(val binding: OkazuLogRvItemBinding): RecyclerView.ViewHolder(binding.root)
}
