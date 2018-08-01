package com.cab.user.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class CustomAdapter(internal var listener: AdapterListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface AdapterListener {
        fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

        fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)

        val itemCount: Int

        fun getItemViewType(position: Int): Int

        fun filterData(countryName: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return listener.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        listener.onBindViewHolder(holder, position)
    }

    override fun getItemCount(): Int {
        return listener.itemCount
    }

    override fun getItemViewType(position: Int): Int {
        return listener.getItemViewType(position)
    }

}