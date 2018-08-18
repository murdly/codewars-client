package com.akarbowy.codewarsclient.ui.detail.binding

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.akarbowy.codewarsclient.R

class LanguageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val textView: TextView = itemView.findViewById(R.id.langaugeView)

    fun bindTo(language: String) {
        textView.text = language
    }
}