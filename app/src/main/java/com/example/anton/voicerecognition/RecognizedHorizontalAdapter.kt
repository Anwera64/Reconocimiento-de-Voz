package com.example.anton.voicerecognition

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_recognized.view.*

class RecognizedHorizontalAdapter(private val items: ArrayList<String>, private val context: Context) : RecyclerView.Adapter<RecognizedHorizontalAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recognized, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvText.text = items[position]
        holder.tvPos.text = position.toString()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvText = view.tvPastText
        val tvPos = view.pos
    }
}