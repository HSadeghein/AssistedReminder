package com.example.assistedreminder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.list_view_item.view.*

class CustomAdapter(context:Context,private val list: Array<String>) : BaseAdapter()
{
    private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val row = inflater.inflate(R.layout.list_view_item, parent, false)
        row.linear.version_number.text = position.toLong().toString()
        row.linear.version_heading.text = list[position] + ": "

        return  row
    }
    override fun getItem(position: Int): Any {

        return list[position]
    }

    override fun getItemId(position: Int): Long {


        return position.toLong()
    }

    override fun getCount(): Int {


        return list.size
    }
}