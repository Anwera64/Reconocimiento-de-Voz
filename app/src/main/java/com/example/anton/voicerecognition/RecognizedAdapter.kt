package com.example.anton.voicerecognition

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_recognized_list.view.*

class RecognizedAdapter(private val items: ArrayList<ArrayList<String>>, private val context: Context) : RecyclerView.Adapter<RecognizedAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recognized_list, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.rvHorizontal.layoutManager =  LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        holder.rvHorizontal.adapter = RecognizedHorizontalAdapter(items[position], context)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rvHorizontal = view.rvHorizontal
    }
}