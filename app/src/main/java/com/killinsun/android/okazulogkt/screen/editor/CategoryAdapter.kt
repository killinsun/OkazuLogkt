package com.killinsun.android.okazulogkt.screen.editor

import android.R
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.killinsun.android.okazulogkt.data.Category


class CategoryAdapter: ArrayAdapter<Category>{

    constructor(context: Context?) : super(context!!, R.layout.simple_spinner_item){
        setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
    }
    constructor(context: Context?, list: MutableList<Category>) : super(
        context!!,
        R.layout.simple_spinner_item,
        list!!
    ){
        setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val textView = super.getView(position, convertView, parent) as TextView
        textView.setText(getItem(position)?.name)
        return textView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val textView = super.getDropDownView(position, convertView, parent) as TextView
        textView.setText(getItem(position)?.name)
        return textView
    }



}

